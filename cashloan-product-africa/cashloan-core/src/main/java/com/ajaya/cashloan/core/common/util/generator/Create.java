package com.ajaya.cashloan.core.common.util.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Create {

    public static final Logger logger = LoggerFactory.getLogger(Create.class);

    public static void main(String[] args) {
        Create ot = new Create();
        ot.test();
    }

    public void test() {

        // 数据库连接信息
        String url = "jdbc:mysql://localhost:3306/world?useUnicode=true&characterEncoding=utf8";
        String MysqlUser = "root";
        String mysqlPassword = "root";

        // 数据库及数据表名称
        String database = "world";
        String table = "cl_user_voice_sms_log";

        // 配置作者及Domain说明
        String classAuthor = "yanzhiqiang";
        String functionName = "用户语音验证日志表";

        // 公共包路径 (例如 BaseDao、 BaseService、 BaseServiceImpl)
        String commonName = "com.ajaya.cashloan.core.common";

        String packageName = "com.ajaya.cashloan.cl";
        String moduleName = "simpleCount";

        //Mapper文件存储地址  默认在resources中
        String batisName = "config/mappers";
        String db = "mysql";

        //类名前缀
        String classNamePrefix = "UserVoiceSmsLog";
        try {
            MybatisGenerate.generateCode(db, url, MysqlUser, mysqlPassword, database, table, commonName, packageName, batisName, moduleName, classAuthor, functionName, classNamePrefix);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
