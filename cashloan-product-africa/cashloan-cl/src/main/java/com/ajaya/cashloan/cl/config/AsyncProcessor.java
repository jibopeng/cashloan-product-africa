package com.ajaya.cashloan.cl.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 功能说明：线程池
 *
 * @author yanzhiqiang
 * @since 2020-04-26 16:43
 **/
public class AsyncProcessor {
   private static final Logger logger = LoggerFactory.getLogger(AsyncProcessor.class);

    /**
     * 默认最大并发数<br>
     */
    private static final int DEFAULT_MAX_CONCURRENT = Runtime.getRuntime().availableProcessors() * 2;

    /**
     * 线程池名称格式
     */
    private static final String THREAD_POOL_NAME = "ExternalConvertProcessPool-%d";

    /**
     * 线程工厂名称
     */
    private static final ThreadFactory FACTORY = new BasicThreadFactory.Builder().namingPattern(THREAD_POOL_NAME)
            .daemon(true).build();

    /**
     * 默认队列大小
     */
    private static final int DEFAULT_SIZE = 500;

    /**
     * 默认线程存活时间
     */
    private static final long DEFAULT_KEEP_ALIVE = 60L;

    /**
     * NewEntryServiceImpl.java:689
     * Executor
     */
    private static ExecutorService executor;
    /**
     * 执行队列
     */
    private static BlockingQueue<Runnable> executeQueue = new ArrayBlockingQueue<>(DEFAULT_SIZE);

    static {
        // 创建 Executor
        // 此处默认最大值改为处理器数量的 4 倍
        try {
            executor = new ThreadPoolExecutor(DEFAULT_MAX_CONCURRENT, DEFAULT_MAX_CONCURRENT * 4, DEFAULT_KEEP_ALIVE,
                    TimeUnit.SECONDS, executeQueue, FACTORY);
            // 关闭事件的挂钩
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

                @Override
                public void run() {
                    AsyncProcessor.logger.info("AsyncProcessor shutting down.");
                    executor.shutdown();
                    try {
                        // 等待1秒执行关闭
                        if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                            AsyncProcessor.logger.error("AsyncProcessor shutdown immediately due to wait timeout.");
                            executor.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        AsyncProcessor.logger.error("AsyncProcessor shutdown interrupted.");
                        executor.shutdownNow();
                    }
                    AsyncProcessor.logger.info("AsyncProcessor shutdown complete.");
                }
            }));
        } catch (Exception e) {
            logger.error("AsyncProcessor init error.", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 此类型无法实例化
     */
    private AsyncProcessor() {
    }

    /**
     * 执行任务，不管是否成功<br>
     * 其实也就是包装以后的 execute 方法
     *
     * @param task 任务
     * @return 执行结果
     */
    public static boolean executeTask(Runnable task) {

        try {
            executor.execute(task);
        } catch (RejectedExecutionException e) {
            logger.error("Task executing was rejected.", e);
            return false;
        }
        return true;
    }

    /**
     * 提交任务，并可以在稍后获取其执行情况<br>
     * 当提交失败时，会抛出 {@link }
     *
     * @param task 任务
     * @return
     */
    public static <T> Future<T> submitTask(Callable<T> task) {

        try {
            return executor.submit(task);
        } catch (RejectedExecutionException e) {
            logger.error("Task executing was rejected.", e);
            throw new UnsupportedOperationException("Unable to submit the task, rejected.", e);
        }
    }
}
