package com.ajaya.cashloan.cl.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author ryan
 * @version 1.0 2020/11/06
 */
public interface SdkModelScoreService {

    /**
     * 保存用户sdk模型分
     *
     * @param userId
     */
    int saveUserSdkModelScore(Long userId, Long borrowId);

    /**
     * 更新回调模型分
     *
     * @param userId
     * @param scores
     */
    void upEpochModelScoreCallBack(Long userId, String scores);

    /**
     * 调用epoch 服务
     *
     * @param bizData
     * @param method
     * @return
     */
    JSONObject requestForEpochServer(Map<String, Object> bizData, String method) throws Exception;
}
