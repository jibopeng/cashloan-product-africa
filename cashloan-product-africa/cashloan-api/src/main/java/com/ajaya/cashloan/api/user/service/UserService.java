package com.ajaya.cashloan.api.user.service;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ajaya.cashloan.cl.domain.UserEquipmentInfo;
import com.ajaya.cashloan.cl.mapper.UserEquipmentInfoMapper;
import com.ajaya.cashloan.cl.model.MessageTypeModel;
import com.ajaya.cashloan.core.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ajaya.cashloan.api.user.bean.AppDbSession;
import com.ajaya.cashloan.api.user.bean.AppSessionBean;
import com.ajaya.cashloan.cl.domain.Channel;
import com.ajaya.cashloan.cl.model.SmsModel;
import com.ajaya.cashloan.cl.service.ChannelService;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.cl.service.UserAppFlyerLogService;
import com.ajaya.cashloan.cl.service.UserEquipmentInfoService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.service.CloanUserService;
import com.google.errorprone.annotations.RestrictedApi;

import tool.util.BeanUtil;

/**
 * Created by lsk on 2016/7/27.
 */
@Service("clUserService_")
@SuppressWarnings({"rawtypes", "unchecked"})
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private AppDbSession appDbSession;
    @Resource
    private DBService dbService;
    @Resource
    private MybatisService mybatisService;
    @Resource
    private SmsService smsService;

    @Resource
    private CloanUserService cloanUserService;
    @Resource
    private UserEquipmentInfoService userEquipmentInfoServiceImpl;
    @Resource
    private UserEquipmentInfoMapper userEquipmentInfoMapper;
    @Resource
    private UserAppFlyerLogService userAppFlyerLogService;


    public UserService() {
        super();
    }

    /**
     * 用户注册
     *  @param request            请求实体 暂时未用
     * @param phone              用户电话号码
     * @param pwd                密码
     * @param vcode              验证码
     * @param invitationCode     邀请码
     * @param registerCoordinate 注册位置（经纬度）
     * @param registerAddr       注册地址
     * @param regClient          注册设备类型（ios,android）
     * @param signMsg            验证签名
     * @param channelCode        渠道代码
     * @param operatingSystem    操作系统
     * @param systemVersions     系统版本
     * @param phoneType          手机型号
     * @param phoneBrand         手机品牌
     * @param phoneMark          手机
     * @param versionName        应用版本号
     * @param versionCode        应用build号
     * @param mac                mac
     * @param appInstallTime     APP安装时间
     * @param appMarket          APP应用市场
     * @param imei               手机设备标识
     * @param androidId
     * @param gpsAdId            @return 注册返回信息 map
     * @param deviceWidth
     * @param deviceHeight
     * @param telephony
     * @param defaultLanguage
     * @param securityPatch
     * @param sdkVersion
     * @param rooted
     * @param productionDate
     * @param serial
     * @param containSD
     * @param ramCanUse
     * @param ramTotal
     * @param cashCanUse
     * @param cashTotal
     * @param extraSD
     * @param appFlag
     */
    @Transactional
    public Map registerUser(HttpServletRequest request, String phone, String pwd, String vcode, String invitationCode,
                            String registerCoordinate, String registerAddr, String regClient, String signMsg, String channelCode,
                            String operatingSystem, String systemVersions, String phoneType, String phoneBrand, String phoneMark,
                            String versionName, String versionCode, String mac, Date appInstallTime, String appMarket, String imei, String ip,
                            String androidId, String gpsAdId,
                            String deviceWidth, String deviceHeight, String telephony, String defaultLanguage, String securityPatch,
                            String sdkVersion, String rooted, String productionDate, String serial,
                            String containSD, String ramCanUse, String ramTotal, String cashCanUse, String cashTotal, String extraSD, String appFlag) {
        try {
            //参数验证
            if (StringUtil.isEmpty(phone) ||  StringUtil.isEmpty(pwd) || StringUtil.isEmpty(vcode) || pwd.length() < 32) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", "The parameters are incorrect.");
                return ret;
            }
            if (!StringUtil.isPhone(phone) ){
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", "Format error");
                return ret;
            }
            //当日注册用户校验是否达到了上线
            CloanUserService cloanUserService = (CloanUserService) BeanUtil.getBean("cloanUserService");
            long todayCount = cloanUserService.todayCount();
            String dayRegisterMaxCount = Global.getValue("day_register_max");
            if (StringUtil.isNotBlank(dayRegisterMaxCount)) {
                int dayRegisterMax = Integer.parseInt(dayRegisterMaxCount);
                if (dayRegisterMax > 0 && todayCount >= dayRegisterMax) {
                    Map ret = new LinkedHashMap();
                    ret.put("success", false);
                    ret.put("msg", "The number of registered users has reached the maximum today. Please come back tomorrow.");
                    return ret;
                }
            }
            //验证手机号和验证码是否过期
            ClSmsService clSmsService = (ClSmsService) BeanUtil.getBean("clSmsService");
            String productName = Global.getValue("title2");
            String type = SmsModel.SMS_TYPE_REGISTER;
            if (productName.equals(appFlag)) {
                type = type + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
            }
            int results = clSmsService.verifySms(phone, type, vcode);
            String vmsg;
            if (results == 1) {
                vmsg = null;
            } else if (results == -1) {
                //验证码过期
                vmsg = "The verification code has expired.";

            } else {
                //手机号码或验证码错误
                vmsg = "Your OTP is incorrect. Please enter the correct number.";
            }
            if (vmsg != null) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", vmsg);
                return ret;
            }
            //验证手机号是否被注册
            Map old = mybatisService.queryRec("usr.queryUserByLoginName", phone);
            if (old != null) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                //该手机号已经被注册了
                ret.put("msg", "The mobile phone number has been registered.");
                return ret;
            }
            //校验该用户使用的手机imei是否已经被注册使用
            logger.info("phone:" + phone + ", imei:" + imei);
            if(!"000000000000000".equals(imei)){
                logger.info("phone:" + phone + ", imei:" + imei + ", 进行imei拦截");
                HashMap<String, Object> param = new HashMap<>(8);
                param.put("imei", imei);
                UserEquipmentInfo userEquipmentInfo = userEquipmentInfoMapper.findSelective(param);
                if (userEquipmentInfo != null) {
                    Map ret = new LinkedHashMap();
                    ret.put("success", false);
                    //手机mac已经被其他手机号注册使用
                    ret.put("msg", "The device you use has registered other phone numbers and cannot be registered.");
                    return ret;
                }
            } else {
                logger.info("phone:" + phone + ", imei:" + imei + ", 不拦截");
            }
            // 渠道
            long channelId = 0;
            System.out.println("channelCode: " + channelCode);
            if (StringUtil.isNotBlank(channelCode)) {
                ChannelService channelService = (ChannelService) BeanUtil.getBean("channelService");
                Channel channel = channelService.findByCode(channelCode);
                if (channel != null) {
                    channelId = channel.getId();
                }
            }
            System.out.println("channelId: " + channelId);
            //生成客户信息
            String uuid = OrderNoUtil.getSerialNumber();
            long userId = dbService.insert(SqlUtil.buildInsertSqlMap("cl_user", new Object[][]{
                    {"login_name", phone},
                    {"login_pwd", pwd},
                    {"invitation_code", randomInvitationCode(6)},
                    {"regist_time", new Date()},
                    {"uuid", uuid},
                    {"level", 3},
                    {"register_client", regClient},
                    {"channel_id", channelId},
                    {"ip", ip}
            }));
            //生成用户基础信息
            dbService.insert(SqlUtil.buildInsertSqlMap("cl_user_base_info", new Object[][]{
                    {"user_id", userId},
                    {"phone", phone},
                    {"create_time", new Date()},
                    {"register_coordinate", registerCoordinate},
                    {"register_addr", registerAddr}
            }));
            //生成用户认证项表
            dbService.insert(SqlUtil.buildInsertSqlMap("cl_user_auth", new Object[][]{
                    {"user_id", userId},
                    {"basic_info_state", 10},
                    {"personal_info_state", 10},
                    {"contact_state", 10},
                    {"bank_account_state", 10},
            }));
            //用户认证时间表
            logger.info("向用户认证时间记录表插入数据,user_id " + userId);
            //向用户认证记录时间表插入数据
            dbService.insert(SqlUtil.buildInsertSqlMap("cl_user_auth_time", new Object[][]{
                    {"user_id", userId},
                    {"create_time", new Date()}
            }));


            //保存用户设备信息表
            UserEquipmentInfo uei = new UserEquipmentInfo();
            uei.setAppInstallTime(appInstallTime);
            uei.setAppMarket(appMarket);
            uei.setUserId(userId);
            uei.setBlackBox("");
            uei.setOperatingSystem(operatingSystem);
            uei.setSystemVersions(systemVersions);
            uei.setPhoneType(phoneType);
            uei.setPhoneBrand(phoneBrand);
            uei.setPhoneMark(phoneMark);
            uei.setVersionName(versionName);
            uei.setDeviceWidth(deviceWidth);
            uei.setDeviceHeight(deviceHeight);
            uei.setTelephony(telephony);
            uei.setDefaultLanguage(defaultLanguage);
            uei.setSecurityPatch(securityPatch);
            uei.setSdkVersion(sdkVersion);
            uei.setRooted(rooted);
            uei.setProductionDate(productionDate);
            uei.setSerial(serial);
            uei.setContainSd(containSD);
            uei.setRamCanUse(ramCanUse);
            uei.setRamTotal(ramTotal);
            uei.setCashCanUse(cashCanUse);
            uei.setCashTotal(cashTotal);
            uei.setExtraSd(extraSD);
            if (!StringUtil.isEmpty(androidId)) {
                uei.setAndroidId(androidId);
            }
            if (!StringUtil.isEmpty(androidId)) {
                uei.setGpsadId(gpsAdId);
            }
            uei.setMac(mac);
            uei.setImei(imei);
            userEquipmentInfoServiceImpl.saveOrUpdate(uei);
            //adjust时间统计
//            if (androidId != null || gpsAdId != null) {
//                AdJustEnventMonitorUtils.adJustEnvent(AdJustEnventMonitorUtils.RESITER_ADJSUT, androidId, gpsAdId);
//            }
            //appFlyer统计
            if(gpsAdId != null){
            	userAppFlyerLogService.appFlyerRegistEnventMonito(gpsAdId, uuid);
            }
            Map result = new LinkedHashMap();
            result.put("success", true);
            result.put("msg", "注册成功");
            //返回新注册用户的user_id
            logger.info("手机号为 {} 的用户注册成功", phone);
            result.put("userId", userId);
            return result;
        } catch (Exception e) {
            logger.error("手机号为 {} 的用户注册失败-{}", phone, e);
            Map ret = new LinkedHashMap();
            ret.put("success", false);
            ret.put("msg", "Registration failed.");
            return ret;
        }
    }


    private String randomInvitationCode(int len) {
        while (true) {
            String str = randomNumAlph(len);
            if (mybatisService.queryRec("usr.queryUserByInvitation", str) == null) {
                return str;
            }
        }
    }

    private static String randomNumAlph(int len) {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        byte[][] list = {
                {48, 57},
                {97, 122},
                {65, 90}
        };
        for (int i = 0; i < len; i++) {
            byte[] o = list[random.nextInt(list.length)];
            byte value = (byte) (random.nextInt(o[1] - o[0] + 1) + o[0]);
            sb.append((char) value);
        }
        return sb.toString();
    }

    /**
     * 忘记密码 修改密码
     *
     * @param phone   手机号
     * @param newPwd  新密码
     * @param vcode   验证码
     * @param signMsg 签名信息  暂时未用
     * @param appFlag
     * @return 返回map
     */
    public Object forgetPwd(String phone, String newPwd, String vcode, String signMsg, String appFlag) {

        String productName = Global.getValue("title2");
        String type = SmsModel.SMS_TYPE_FINDREG;
        if (productName.equals(appFlag)) {
            type = type + MessageTypeModel.MESSAGE_TYPE_SUFFIX;
        }
        //返回map对象
        Map ret = new LinkedHashMap();
        if (!StringUtil.isEmpty(vcode)) {
            String msg = smsService.validateSmsCode(phone, type, vcode);
            if (msg != null) {
                ret.put("success", false);
                ret.put("msg", msg);
                return ret;
            }
        }
        if (dbService.update(SqlUtil.buildUpdateSql("cl_user", MapUtil.array2Map(new Object[][]{
                {"login_name", phone},
                {"login_pwd", newPwd},
                {"loginpwd_modify_time", new Date()}
        }), "login_name")) == 1) {
            ret.put("success", true);
            //密码已经被修改
            ret.put("msg", "The password has been changed.");
            logger.info("手机号为 {} 的用户修改密码成功", phone);
            return ret;
        } else {
            ret.put("success", false);
            //密码修改失败
            ret.put("msg", "Password modification failed.");
            logger.info("手机号为 {} 的用户修改密码失败", phone);
            return ret;
        }

    }

    /**
     * @param request   请求实体
     * @param loginName 登录名称
     * @param loginPwd  登录密码
     * @param signMsg   验签名信息 暂时未用
     * @param blackBox  验证码
     * @return 登录返回信息 map
     */
    public Map login(final HttpServletRequest request, final String loginName, final String loginPwd, String signMsg, String blackBox, String mac, String imei) {
        try {
            Map user = mybatisService.queryRec("usr.queryUserByLoginName", loginName);
            if (user == null) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", "The account does not exist.");
                return ret;
            }
            String dbPwd = (String) user.get("login_pwd");
            if (dbPwd.equalsIgnoreCase(loginPwd)) {
                AppSessionBean session = appDbSession.create(request, loginName);
                //修改登陆时间
                cloanUserService.modify(loginName);
                userEquipmentInfoServiceImpl.save(loginName, blackBox);
                Map ret = new LinkedHashMap();
                ret.put("success", true);
                //登录成功
                ret.put("msg", "The login was successful.");
                ret.put("data", session.getFront());
                logger.info("手机号为 {} 的用户登录成功", loginName);
                return ret;
            }
            Map ret = new LinkedHashMap();
            ret.put("success", false);
            //密码错误
            ret.put("msg", "The password is wrong.");
            logger.info("手机号为 {} 的用户登录失败-密码错误", loginName);
            return ret;
        } catch (Exception e) {
            logger.info("手机号为 {} 的用户登录异常-{}", loginName, e);
            Map ret = new LinkedHashMap();
            ret.put("code", 500);
            //系统异常
            ret.put("msg", "System abnormalities.");
            return ret;
        }
    }


    /**
     * 校验该用户使用的手机imei是否已经被注册使用
     * @return
     */
    public boolean checkImei(final String imei){
        if (!"000000000000000".equals(imei)&& !StringUtil.isEmpty(imei)){
            HashMap<String, Object> param = new HashMap<>(8);
            param.put("imei", imei);
            UserEquipmentInfo userEquipmentInfo = userEquipmentInfoMapper.findSelective(param);
            if (userEquipmentInfo != null) {
                //手机mac已经被其他手机号注册使用
                return false;
            }
        }
        return true;
    }
}