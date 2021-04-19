package com.ajaya.cashloan.cl.service.impl;

import com.ajaya.cashloan.cl.domain.UserLoginVersionLog;
import com.ajaya.cashloan.cl.mapper.UserLoginVersionLogMapper;
import com.ajaya.cashloan.cl.service.UserLoginVersionLogService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * '用户登录版本记录表ServiceImpl
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-17 12:45:56
 */
 
@Service("userLoginVersionLogService")
public class UserLoginVersionLogServiceImpl extends BaseServiceImpl<UserLoginVersionLog, Long> implements UserLoginVersionLogService {
	
    private static final Logger logger = LoggerFactory.getLogger(UserLoginVersionLogServiceImpl.class);
   
    @Resource
    private UserLoginVersionLogMapper userLoginVersionLogMapper;

	@Override
	public BaseMapper<UserLoginVersionLog, Long> getMapper() {
		return userLoginVersionLogMapper;
	}

    @Override
    public void saveUserLoginInfo(String userId, String versionNumber, String appFlag) {
	    try {
            //如果用户id为空，则为未登录用户，不需要进行记录
            if (StringUtil.isEmpty(userId)){
                return;
            }
            //如果没有标志说明为RupeeMax
            if (StringUtil.isEmpty(appFlag)){
                appFlag =  Global.getValue("title");
            }
            Date now = new Date();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(now);
            HashMap<String, Object> hashMap = new HashMap<>(8);
            hashMap.put("userId",Long.parseLong(userId));
            hashMap.put("productName",appFlag);
            hashMap.put("loginTime",date);
            List<UserLoginVersionLog> userLoginVersionLogs = userLoginVersionLogMapper.listSelective(hashMap);
            //则不需要添加新的记录
            if (userLoginVersionLogs.size()>0){
                return;
            }
            UserLoginVersionLog userLoginVersionLog = new UserLoginVersionLog();
            userLoginVersionLog.setCreateTime(now );
            userLoginVersionLog.setLoginTime(date);
            userLoginVersionLog.setUserId(Long.parseLong(userId));
            userLoginVersionLog.setProductName(appFlag);
            userLoginVersionLog.setVersion(versionNumber);
            userLoginVersionLogMapper.save(userLoginVersionLog);
            logger.info("用户 {} 日版本记录保存成功！",userId );
        }catch (Exception e){
	        e.printStackTrace();
        }

    }
}