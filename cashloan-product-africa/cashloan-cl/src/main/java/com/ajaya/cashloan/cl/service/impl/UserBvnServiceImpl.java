package com.ajaya.cashloan.cl.service.impl;

import javax.annotation.Resource;
import javax.swing.plaf.nimbus.State;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.ajaya.cashloan.cl.domain.UserBvn;
import com.ajaya.cashloan.cl.domain.UserBvnLog;
import com.ajaya.cashloan.cl.mapper.UserBvnLogMapper;
import com.ajaya.cashloan.cl.mapper.UserBvnMapper;
import com.ajaya.cashloan.cl.model.BvnCheck;
import com.ajaya.cashloan.cl.service.UserBvnService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.util.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.javassist.expr.NewExpr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;

import java.util.Date;
import java.util.HashMap;


/**
 * '用户bvn记录表ServiceImpl
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-15 17:23:05
 */

@Service("userBvnService")
public class UserBvnServiceImpl extends BaseServiceImpl<UserBvn, Long> implements UserBvnService {

    private static final Logger logger = LoggerFactory.getLogger(UserBvnServiceImpl.class);

    private static final String BVM_URL = "https://api.paystack.co/bank/resolve_bvn/";


    @Resource
    private UserBvnMapper userBvnMapper;
    @Resource
    private UserBvnLogMapper userBvnLogMapper;

    @Override
    public BaseMapper<UserBvn, Long> getMapper() {
        return userBvnMapper;
    }

    @Override
    public BvnCheck checkBvn(String userIdStr, String bvnNumber, String phone) {
        BvnCheck bvnCheck = new BvnCheck();
        bvnCheck.setBvn(false);
        //如果查询已经添加过，不进行请求认证
        HashMap<String, Object> param = new HashMap<>();
        param.put("userId",userIdStr);
        param.put("bvn",bvnNumber);
        UserBvn selective = userBvnMapper.findSelective(param);
        if (selective!=null){
             bvnCheck.setBvn(true);
            return bvnCheck;
        }
        long userId = Long.parseLong(userIdStr);
        //如果之前解析成功过则拿数据库信息，则拿数据库里的信息
        param.clear();
        param.put("userId", userId);
        param.put("param", bvnNumber);
        param.put("status", "true");
        UserBvnLog bvnLog = userBvnLogMapper.findSelectiveNew(param);
        String result;
        boolean saveLog =false;
        if (bvnLog != null) {
            result = bvnLog.getResponseParam();

        } else {
            HashMap<String, String> header = new HashMap<>(1);
            header.put("Authorization", Global.getValue("pay_stack_secret_key"));
            String url = BVM_URL + bvnNumber;
            result = HttpClientUtil.sentGet(url, header, null, "utf-8");
            saveLog=true;
        }
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            Boolean status = resultObj.getBoolean("status");
            String message = resultObj.getString("message");
            //保存日志
            if (saveLog) {
                saveLog(userId, bvnNumber, result, status, message);
            }
            if (status) {
                JSONObject data = resultObj.getJSONObject("data");
                String mobile = data.getString("mobile");
                if (mobile.contains(phone)) {
                    //保存记录
                    saveBvn(bvnNumber, userId, status, data,message);
                    bvnCheck.setBvn(true);
                    return bvnCheck;

                } else {
                    bvnCheck.setMessage("The BVN account does not belong to you.");
                    return bvnCheck;
                }
            }
            bvnCheck.setMessage(message);
            return bvnCheck;
        } else {
            logger.info("用户 {} 调用bvn验证接口，返回空", userId);
            bvnCheck.setMessage("BVN validation failed. Please try again later");
            return bvnCheck;
        }
    }

    private void saveBvn(String bvnNumber, long userId, Boolean status, JSONObject data,String message) {
        UserBvn userBvn = new UserBvn();
        userBvn.setCreateTime(new Date());
        userBvn.setBvn(bvnNumber);
        userBvn.setUserId(userId);
        userBvn.setDob(data.getString("dob"));
        userBvn.setFormattedDob(data.getString("formatted_dob"));
        userBvn.setFirstName(data.getString("first_name"));
        userBvn.setLastName(data.getString("last_name"));
        userBvn.setMobile(data.getString("mobile"));
        userBvn.setMessage(message);
        userBvn.setStatus(Boolean.toString(status));
        userBvnMapper.save(userBvn);
    }

    private void saveLog(Long userId, String bvnNumber, String result, Boolean status, String message) {
        UserBvnLog userBvnLog = new UserBvnLog();
        userBvnLog.setCreateTime(new Date());
        userBvnLog.setMessage(message);
        userBvnLog.setStatus(Boolean.toString(status));
        userBvnLog.setParam(bvnNumber);
        userBvnLog.setUserId(userId);
        userBvnLog.setResponseParam(result);
        userBvnLogMapper.save(userBvnLog);
    }
}