package com.ajaya.cashloan.api.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ajaya.cashloan.api.user.bean.AppAbsActionWrapper;
import com.ajaya.cashloan.api.user.bean.AppDbSession;
import com.ajaya.cashloan.api.user.bean.AppSessionBean;
import com.ajaya.cashloan.api.user.service.MybatisService;
import com.ajaya.cashloan.api.user.service.SmsService;
import com.ajaya.cashloan.api.user.service.UserService;
import com.ajaya.cashloan.core.common.util.StringUtil;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 功能说明：用户登录相关业务
 *
 * @author yanzhiqiang
 * @since 2020-02-23 17:21
 */
@Scope("prototype")
@Controller
@RequestMapping("/api/user")
public class LoginController {

    @Resource
    private MybatisService mybatisService;
    @Resource
    private AppDbSession appDbSession;
    @Resource(name = "clUserService_")
    private UserService userService;

    /**
     * 用户注册
     *
     * @param request            请求实体
     * @param response           相应实体
     * @param loginName          登录名称
     * @param loginPwd           登录密码
     * @param vcode              验证码
     * @param invitationCode     邀请码
     * @param client             客户端类型（ios/android）
     * @param registerCoordinate 注册位置（经纬度）
     * @param registerAddr       注册地址
     * @param signMsg            验证签名信息
     * @param channelCode        渠道代码
     * @param blackBox           设备指纹
     * @param operatingSystem    操作系统
     * @param systemVersions     系统版本
     * @param phoneType          手机设备标识
     * @param phoneBrand         手机品牌
     * @param phoneMark          手机
     * @param versionName        应用版本号
     * @param versionCode        应用build号
     * @param mac                mac
     * @param imei               手机设备地址标识
     * @param appInstallTime     APP安装时间
     * @param appMarket          APP应用市场
     * @param ip                 注册用户的id
     * @param androidId          android id
     * @param appFlag            app名称
     * @param cashCanUse         可用内存
     * @param cashTotal          内存总共
     * @param containSD          是否有内置sd卡
     * @param defaultLanguage    默认语言
     * @param deviceHeight       分辨率高
     * @param deviceWidth        分辨率屏幕宽
     * @param extraSD            是否有外置SD卡
     * @param ramCanUse          ram可用值
     * @param gpsAdId            adjusted 唯一标识
     * @param productionDate     出厂日期 yyyy-MM-dd格式
     * @param ramTotal           ram总共
     * @param rooted             是否root
     * @param sdkVersion         android.os.Build.VERSION.RELEASE
     * @param securityPatch      安全更新日期
     * @param serial             设备序列号
     * @param telephony          运营商
     */

    @RequestMapping("register")
    public void register(
            final HttpServletRequest request, final HttpServletResponse response, final String loginName,
            final String loginPwd, final String vcode, final String invitationCode, final String client,
            final String registerCoordinate, final String registerAddr, final String signMsg, final String imei,
            final String blackBox, final String operatingSystem, final String systemVersions, final String phoneType,
            final String phoneBrand, final String phoneMark, final String versionName,
            final String versionCode, final String mac, final Date appInstallTime, final String appMarket, final String channelCode,
            final String ip, @RequestParam(required = false) final String androidId, @RequestParam(required = false) final String gpsAdId,
            @RequestParam(required = false) final String deviceWidth, @RequestParam(required = false) final String deviceHeight,
            @RequestParam(required = false) final String telephony, @RequestParam(required = false) final String defaultLanguage,
            @RequestParam(required = false) final String securityPatch, @RequestParam(required = false) final String sdkVersion,
            @RequestParam(required = false) final String rooted, @RequestParam(required = false) final String productionDate,
            @RequestParam(required = false) final String serial,
            @RequestParam(required = false) final String containSD, @RequestParam(required = false) final String ramCanUse,
            @RequestParam(required = false) final String ramTotal, @RequestParam(required = false) final String cashCanUse,
            @RequestParam(required = false) final String cashTotal, @RequestParam(required = false) final String extraSD,
            @RequestParam(required = false) final String appFlag) {
        new AppAbsActionWrapper(response) {
            @Override
            public Object doAction() {
                Map result = userService.registerUser(request, loginName, loginPwd, vcode, invitationCode, registerCoordinate,
                        registerAddr, client, signMsg, channelCode, operatingSystem, systemVersions, phoneType, phoneBrand, phoneMark
                        , versionName, versionCode, mac, appInstallTime, appMarket, imei, ip, androidId, gpsAdId,
                        deviceWidth, deviceHeight, telephony, defaultLanguage,
                        securityPatch, sdkVersion, rooted, productionDate, serial,
                        containSD, ramCanUse, ramTotal, cashCanUse, cashTotal, extraSD, appFlag);

                if ((Boolean) result.get("success")) {
                    result = userService.login(request, loginName, loginPwd, signMsg, blackBox, mac, imei);
                    result.put("msg", result.get("msg"));
                }
                return result;
            }
        };
    }

    /**
     * 用户录登
     *
     * @param request   请求实体
     * @param response  响应实体
     * @param loginName 登录名称
     * @param loginPwd  登录密码
     * @param signMsg   验签名信息
     * @param blackBox  验证码
     * @param mac       手机网络地址
     * @param imei      手机设备地址标识
     */
    @RequestMapping("login")
    public void login(final HttpServletRequest request,
                      HttpServletResponse response, final String loginName, final String mac, final String imei,
                      final String loginPwd, final String signMsg, final String blackBox) {
        new AppAbsActionWrapper(response) {
            @Override
            public Object doAction() {
                return userService.login(request, loginName, loginPwd, signMsg, blackBox, mac, imei);
            }
        };
    }

    /**
     * 自动登录
     *
     * @param request       请求实体
     * @param response      响应实体
     * @param refresh_token 刷新token
     */
    @RequestMapping("autoLogin")
    public void autoLogin(final HttpServletRequest request,
                          final HttpServletResponse response, final String refresh_token) {
        new AppAbsActionWrapper(response) {
            @Override
            public Object doAction() {
                Map<String, Object> result = new LinkedHashMap<>();
                if (StringUtil.isEmpty(refresh_token)) {
                    result.put("success", false);
                    //非法登录
                    result.put("msg", "Illegal requests.");
                    return result;
                }
                Object obj = appDbSession.autoLogin(request, refresh_token);
                if (!(obj instanceof AppSessionBean)) {
                    return obj;
                }
                AppSessionBean bean = (AppSessionBean) obj;
                result.put("success", true);
                result.put("data", bean.getFront());
                //成功自动登录
                result.put("msg", "Successful automatic login.");
                return result;
            }
        };
    }

    /**
     * 忘记密码 修改密码
     *
     * @param request  请求实体
     * @param response 响应实体
     * @param phone    手机号
     * @param newPwd   新密码
     * @param vcode    验证码
     * @param signMsg  签名
     */
    @RequestMapping("login/forgetPwd.htm")
    public void forgetPwd(final HttpServletRequest request,
                          HttpServletResponse response, final String phone,
                          final String newPwd, final String vcode, final String signMsg, @RequestParam(required = false) final String appFlag) {
        new AppAbsActionWrapper(response) {
            @Override
            public Object doAction() {
                return userService.forgetPwd(phone, newPwd, vcode, signMsg, appFlag);
            }
        };
    }


    /**
     * 手机号是否可以注册/是否为已注册用户
     *
     * @param request  请求实体
     * @param response 响应实体
     * @param phone    手机号
     */
    @RequestMapping("isPhoneExists")
    public void isPhoneExists(final HttpServletRequest request,
                              HttpServletResponse response, final String phone, final String imei) {
        new AppAbsActionWrapper(response) {
            @Override
            public Object doAction() {
                boolean exists = mybatisService.queryRec("usr.isPhoneExists",
                        phone) != null;
                Map<String, Object> ret = new LinkedHashMap<>();
                ret.put("success", true);
                Map<String, String> data = new LinkedHashMap<>();
                data.put("isExists", exists ? "20" : "10");
                ret.put("data", data);
                //exists ? "该手机号码已存在" : "该手机不存在，可注册"
                ret.put("msg", exists ? "The mobile phone number already exists." : "The mobile phone does not exist and can be registered.");
                //判断手机imei是否被注册过(手机号没注册过的情况下)
                if (!exists) {
                    boolean isHave = userService.checkImei(imei);
                    if (!isHave) {
                        //手机号可以注册的时候, 设备手机imei已经被注册(一台手机不允许多个号码注册)
                        ret.put("msg", "The device you use has registered other phone numbers and cannot be registered.");
                        ret.put("success", false);
                        data.put("isExists", "20");
                    }
                }
                return ret;
            }
        };
    }
}
