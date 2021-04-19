package com.ajaya.cashloan.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.api.user.bean.AppLoginedActionWraper;
import com.ajaya.cashloan.api.user.service.DBService;
import com.ajaya.cashloan.api.user.service.MybatisService;
import com.ajaya.cashloan.cl.domain.Channel;
import com.ajaya.cashloan.cl.domain.UserEquipmentInfo;
import com.ajaya.cashloan.cl.mapper.UserEquipmentInfoMapper;
import com.ajaya.cashloan.cl.model.AppFlyerModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.ChannelService;
import com.ajaya.cashloan.cl.service.UserAppFlyerLogService;
import com.ajaya.cashloan.core.common.util.AppFlyerUtils;
import com.ajaya.cashloan.core.common.util.ReqToJsonUtil;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.SqlUtil;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.core.domain.User;
import com.ajaya.cashloan.core.service.CloanUserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import tool.util.BeanUtil;

/**
 * 用户登录controller
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-03-18 15:54:41
 */
@Scope("prototype")
@Controller
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Resource
    private DBService dbService;

    @Resource
    private MybatisService mybatisService;

    @Resource
    private UserEquipmentInfoMapper userEquipmentInfoMapper;
    @Resource(name = "cloanUserService")
    private CloanUserService cloanUserService;
    @Resource
    private UserAppFlyerLogService userAppFlyerLogService;

    @RequestMapping("/api/act/user/changeLoginPwd")
    public void changeLoginPwd(final HttpServletRequest request,
                               HttpServletResponse response, final String oldPwd,
                               final String newPwd) {
        new AppLoginedActionWraper(response, request) {
            @Override
            public Object doAction(Map userData, String userId) {
                if (StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd)) {
                    Map ret = new LinkedHashMap();
                    ret.put("success", false);
                    ret.put("msg", "Parameters cannot be null.");
                    return ret;
                }
                Map user = mybatisService.queryRec("usr.queryUser", userId);

                if (!oldPwd.equalsIgnoreCase((String) user.get("login_pwd"))) {
                    Map ret = new LinkedHashMap();
                    ret.put("success", false);
                    ret.put("msg", "The original password is incorrect.");
                    return ret;
                }

                dbService.update(SqlUtil.buildUpdateSql("cl_user",
                        new Object[][]{{"id", user.get("id")},
                                {"login_pwd", newPwd},
                                {"loginpwd_modify_time", new Date()}}));

                Map ret = new LinkedHashMap();
                ret.put("success", true);
                ret.put("msg", "The login password was changed successfully.");
                return ret;
            }
        };
    }



    /**
     * adjust 用户注册回调，变更注册渠道
     *
     * @param gpsadId     adjust标识
     * @param networkName 渠道code
     * @param androidId   adjust标识
     */
    @RequestMapping(value = "/user/register/callback.htm")
    public void getUserPhone(@RequestParam(value = "google_ads_id", required = false) String gpsadId,
                             @RequestParam(value = "network_name") String networkName,
                             @RequestParam(value = "android_id", required = false) String androidId,
                             @RequestParam(value = "campaign_name",required = false) String campaignName) {
        Map<String, Object> paramMap = new HashMap<>(8);
        String uacFlag="Adwords UAC Installs";
        String uacFlag2="Google Ads UAC";
        String faceBookFlag3="Off-Facebook Installs";
        String faceBookFlag4="Facebook Installs";
        String faceBookFlag5="Instagram Installs";
        String faceBookFlag6="Facebook Messenger";
        if (gpsadId != null) {
            paramMap.put("gpsadId", gpsadId);
        }
        if (androidId != null) {
            paramMap.put("androidId", androidId);
        }
        UserEquipmentInfo userEquipmentInfo = userEquipmentInfoMapper.findLastUserEquipment(paramMap);
        if (userEquipmentInfo != null) {
            // 渠道
            if (StringUtil.isNotBlank(networkName)) {
                ChannelService channelService = (ChannelService) BeanUtil.getBean("channelService");
                //如果是uac投放,则去下一级campaign name 进行区分
                if (uacFlag.equals(networkName)||uacFlag2.equals(networkName)||faceBookFlag3.equals(networkName)||faceBookFlag4.equals(networkName)
                        ||faceBookFlag5.equals(networkName)||faceBookFlag6.equals(networkName)){
                    String[] split = campaignName.split("_");
                    networkName=split[0];
                }
                Channel channel = channelService.findByCode(networkName);
                if (channel != null) {
                    long channelId = channel.getId();
                    dbService.update(SqlUtil.buildUpdateSql("cl_user",
                            new Object[][]{{"id", userEquipmentInfo.getUserId()},
                                    {"channel_id", channelId}}));
                    logger.info("androidId {} gpsadId {} 修改渠道成功：" + networkName, androidId, gpsadId);
                } else {
                    logger.info("networkName {} 未查找到渠道 campaign_name {}", networkName,campaignName);
                }
            } else {
                logger.info("networkName为空 {} campaign_name {}", networkName,campaignName);
            }
        } else {
            logger.info("androidId {} gpsadId {} 未查到响应用户", androidId, gpsadId);
        }
        ServletUtils.writeToResponse(response, ResponseWrapper.success());
    }
    
    /**
     * appFlyer 用户注册回调，变更注册渠道
     *
     * @param gpsadId     adjust标识
     * @param networkName 渠道code
     * @param androidId   adjust标识
     */
    @RequestMapping(value = "/user/register/appFlyerCallback.htm")
    public void appFlyerCallback() {
    	try {
    		ChannelService channelService = (ChannelService) BeanUtil.getBean("channelService");
			ServletInputStream inputStream = request.getInputStream();
			String requestJson = ReqToJsonUtil.parseRequestToJson(inputStream);
			logger.info("appFlyer 回调  {} ", requestJson);
			if(StringUtil.isBlank(requestJson)){
				ServletUtils.writeToResponse(response, ResponseWrapper.error());
				return;
			}
			AppFlyerModel appFlyerModel = JSON.parseObject(requestJson, AppFlyerModel.class);
			String event_name = appFlyerModel.getEvent_name();//时间名
			String event_value = appFlyerModel.getEvent_value();
			String campaign = appFlyerModel.getCampaign();//channel code
			String event_time = appFlyerModel.getEvent_time();
			JSONObject eventValue = JSON.parseObject(event_value);
			String uuid = "";
			if(event_value != null && event_value.contains("uuid")){
				uuid = eventValue.get("uuid").toString();
			}
			if(StringUtil.isNotBlank(uuid) && StringUtil.isNotBlank(campaign) && AppFlyerUtils.RESITER_APPFLYER.equals(event_name)){
				User user = cloanUserService.getUserByUuid(uuid);
				
				//取前缀
				if(campaign.contains("_")){
					String[] split = campaign.split("_");
					campaign = split[0];
					logger.info("appFlyer 回调 campaign 取前缀后的结果 {} ", campaign);
				}
				
				Channel channel = channelService.findByCode(campaign);
				if(user != null && channel != null){
					long channelId = channel.getId();
                    int update = dbService.update(SqlUtil.buildUpdateSql("cl_user",
                            new Object[][]{{"id", user.getId()},
                                    {"channel_id", channelId}}));
                    logger.info("userId {} appFlyer 更改渠道结果 {} ", user.getId(), update);
                    userAppFlyerLogService.save(uuid, event_name + "_callback", requestJson, event_time);
				}
			} else {
				logger.info("appFlyer 回调  {} 不需要更新用户渠道信息", requestJson);
			}
		} catch (Exception e) {
			logger.error("appFlyer 用户注册回调, 报错" , e);
		}
		ServletUtils.writeToResponse(response, ResponseWrapper.success());
    }
}
