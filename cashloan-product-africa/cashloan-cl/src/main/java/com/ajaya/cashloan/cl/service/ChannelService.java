package com.ajaya.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.domain.Channel;
import com.ajaya.cashloan.cl.model.ChannelCountModel;
import com.ajaya.cashloan.cl.model.ChannelModel;
import com.ajaya.cashloan.core.common.service.BaseService;

/**
 * 渠道信息Service
 *
 * @author yanzhiqiang
 * @date 2020-02-27 10:52:07
 */
public interface ChannelService extends BaseService<Channel, Long> {

    /**
     * 保存渠道信息
     *
     * @param channel 渠道实体
     * @return 保存是否成功
     */
    boolean save(Channel channel);

    /**
     * 更新渠道信息
     *
     * @param paramMap 条件map
     * @return 是否成功
     */
    boolean update(Map<String, Object> paramMap);


    /**
     * 根据code查询对象
     *
     * @param code 渠道代码
     * @return Channel实体
     */
    Channel findByCode(String code);

    /**
     * 列表查询渠道信息
     *
     * @param current   当前页
     * @param pageSize  当前页大小
     * @param searchMap 查询条件
     * @return 分页查询列表
     */
    Page<ChannelModel> page(int current, int pageSize,
                            Map<String, Object> searchMap);

    /**
     * 渠道用户统计
     *
     * @param current   当前页
     * @param pageSize  当前页大小
     * @param searchMap 查询条件
     * @return 分页查询列表
     */
    Page<ChannelCountModel> channelUserList(int current, int pageSize,
                                            Map<String, Object> searchMap);

    /**
     * 查出所有渠道信息
     *
     * @return 渠道列表
     */
    List<Channel> listChannel();

    /**
     * 查询没有版本信息的渠道id和名称
     *
     * @return 渠道列表
     */
    List<Channel> listChannelWithoutApp();

    /**
     * 渠道用户统计
     *
     * @param current  当前页
     * @param pageSize 当前页大小
     * @param paramMap 查询条件
     * @return 查询列表
     */
    List<Map<String, Object>> channelUserCount(Map<String, Object> paramMap, int current, int pageSize);
}
