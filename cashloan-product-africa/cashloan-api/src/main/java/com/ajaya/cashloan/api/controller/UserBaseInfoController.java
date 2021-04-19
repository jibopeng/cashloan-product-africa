package com.ajaya.cashloan.api.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.UserEquipmentInfo;
import com.ajaya.cashloan.cl.model.BvnCheck;
import com.ajaya.cashloan.cl.service.*;
import com.ajaya.cashloan.core.common.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ajaya.cashloan.cl.domain.UserAuth;
import com.ajaya.cashloan.cl.domain.UserAuthTime;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.service.UserBaseInfoService;


/**
 * 用户详情表Controller
 *
 * @author yanzhiqiang
 * @since 2021-01-01 11:08:04
 */
@Controller
@Scope("prototype")
public class UserBaseInfoController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(UserBaseInfoController.class);

    @Resource
    private UserBaseInfoService userBaseInfoService;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private UserContactsService userContactsService;

    @Resource
    private UserMessagesService userMessagesServiceImpl;
    @Resource
    private UserAuthTimeService userAuthTimeService;
    @Resource
    private UserEquipmentInfoService userEquipmentInfoServiceImpl;

    @Resource
    private UserBvnService userBvnServiceImpl;

    /**
     * 个人信息保存，以及bvn账号验证
     *
     * @param firstName  名字
     * @param middleName 中间名字
     * @param surName    姓氏
     * @param userId     用户id
     * @param gender     性别
     * @param birthDate  生日
     * @param bvnNumber  bvm账号
     * @param email      邮箱
     */
    @RequestMapping(value = "/api/act/mine/userInfo/personalInfoAuthSave.htm", method = RequestMethod.POST)
    public void personalInfoAuthSave(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "middleName") String middleName,
                                     @RequestParam(value = "surName") String surName, @RequestParam(value = "userId") String userId,
                                     @RequestParam(value = "gender") String gender, @RequestParam(value = "birthDate") String birthDate,
                                     @RequestParam(value = "bvnNumber") String bvnNumber, @RequestParam(value = "email") String email,
                                     @RequestParam(value = "registerCoordinate") String registerCoordinate,
                                     @RequestParam(value = "registerAddr") String registerAddr, @RequestParam(value = "imei", required = false) String imei) {
        //参数非空标识
        boolean paramFlag = StringUtil.isEmpty(firstName.trim()) || StringUtil.isEmpty(middleName.trim()) ||
                StringUtil.isEmpty(surName.trim()) || StringUtil.isEmpty(gender.trim()) || StringUtil.isEmpty(birthDate.trim()) ||
                StringUtil.isEmpty(bvnNumber.trim()) || StringUtil.isEmpty(email.trim()) || StringUtil.isEmpty(userId.trim());
        if (paramFlag) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("Parameters cannot be empty."));
            return;
        }
        //邮箱认证
        if (!StringUtil.isMail(email)) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("Email format is incorrect."));
            return;
        }
        //姓名校验
        if (!ValidInfoUtils.nameCheck(firstName)) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("The format of the FirstName is incorrect."));
            return;
        }
        if (!ValidInfoUtils.nameCheck(surName)) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("The format of the SurName is incorrect."));
            return;
        }
        //BVN格式校验
       /* if (!ValidInfoUtils.isBvn(bvnNumber)) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("BVN format is incorrect."));
            return;
        }*/
        Map<String, Object> paramMap = new HashMap<>(8);
        // PAN卡唯一性校验
        paramMap.put("bvnNo", bvnNumber);
        UserBaseInfo baseInfo = userBaseInfoService.findSelective(paramMap);
        if (baseInfo != null) {
            logger.info("用户：" + userId + " bvn号唯一性校验未通过，系统查询出相同的Bvn号 ------");
            if (!baseInfo.getUserId().toString().equals(userId)) {
                //其他注册账号已经认证过了
                ServletUtils.writeToResponse(response, ResponseWrapper.error("The BVN has been authenticated by other users."));
                return;
            } else {
                //该账号已经认证过了
                ServletUtils.writeToResponse(response, ResponseWrapper.error("The BVN card has been authenticated."));
                return;
            }
        }
        UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(Long.parseLong(userId));
        //bvn校验
        /*BvnCheck bvnCheck = userBvnServiceImpl.checkBvn(userId, bvnNumber, userBaseInfo.getPhone());
        if (!bvnCheck.isBvn()) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error(bvnCheck.getMessage()));
            return;
        }*/


        Boolean isSuccess = userBaseInfoService.personalInfoAuth(firstName, middleName, surName, birthDate, bvnNumber,
                email, gender, userId, registerCoordinate, registerAddr);
        //保存实名信息失败
        if (!isSuccess) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("The personal information authentication failed."));
            return;
        }
        //更新imei
        if (imei != null) {
            UserEquipmentInfo userEquipmentInfo = userEquipmentInfoServiceImpl.findSelective(Long.parseLong(userId));
            userEquipmentInfo.setImei(imei);
            int i = userEquipmentInfoServiceImpl.updateById(userEquipmentInfo);

            logger.info("用户 {} 实名认证更新imei结果："+(i>0),userId);
        }
        //更新认证项表/更新认证时间表
        paramMap.put("userId", userId);
        paramMap.put("personalInfoState", "30");
        userAuthService.updateByUserId(paramMap);
        UserAuthTime userAuthTime = userAuthTimeService.getUserAuthTimeByUserId(Long.parseLong(userId));
        if (userAuthTime.getPersonalInfoAuthOverTime() == null) {
            //首次认证
            userAuthTime.setPersonalInfoAuthOverTime(new Date());
        } else {
            //非首次认证
            userAuthTime.setPersonalInfoAuthOverTime(new Date());
        }
        userAuthTimeService.updateSelectiveByObject(userAuthTime);
        logger.info("用户 {} 实名认证成功", userId);
        ServletUtils.writeToResponse(response, ResponseWrapper.success("The personal information authentication was successful."));
    }


    /**
     * 详细信息保存
     *
     * @param marital                  婚姻状态(必传)
     * @param childrenNumber           孩子数量(必传)
     * @param educationalQualification 学历(必传)
     * @param occupation               职位(必传)
     * @param salarySource             薪资发放方式(必传)
     * @param salaryRange              工资范围(必传)
     * @param loanPurpose              借款目的(必传)
     * @param accommodationType        居住类型(必传)
     * @param stateAddr                邦信息
     * @param homeAddress              家庭住址
     * @param userId                   用户id(必传)
     */
    @RequestMapping(value = "/api/act/mine/userInfo/basicInfoAuth.htm", method = RequestMethod.POST)
    public void basicInfoAuth(@RequestParam(value = "educationalQualification") String educationalQualification,
                              @RequestParam(value = "accommodationType") String accommodationType,
                              @RequestParam(value = "marital") String marital,
                              @RequestParam(value = "childrenNumber") String childrenNumber,
                              @RequestParam(value = "loanPurpose") String loanPurpose,
                              @RequestParam(value = "occupation") String occupation,
                              @RequestParam(value = "salaryRange") String salaryRange,
                              @RequestParam(value = "userId") String userId,
                              @RequestParam(value = "state") String stateAddr,
                              @RequestParam(value = "homeAddress") String homeAddress,
                              @RequestParam(value = "salarySource") String salarySource) {
        //参数非空标识
        boolean paramFlag = StringUtil.isEmpty(marital.trim()) || StringUtil.isEmpty(homeAddress.trim()) ||
                StringUtil.isEmpty(educationalQualification.trim()) ||
                StringUtil.isEmpty(occupation.trim()) || StringUtil.isEmpty(salaryRange.trim()) ||
                StringUtil.isEmpty(accommodationType.trim()) || StringUtil.isEmpty(userId.trim()) ||
                StringUtil.isEmpty(loanPurpose.trim()) || StringUtil.isEmpty(childrenNumber.trim()) ||
                StringUtil.isEmpty(salarySource.trim()) || StringUtil.isEmpty(stateAddr.trim());

        //如果参数有一个为空则返回信息
        if (paramFlag) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("Parameters cannot be empty."));
            return;
        }


        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("userId", Long.parseLong(userId));
        UserBaseInfo info = userBaseInfoService.findSelective(paramMap);
        if (info == null) {
            //未找到用户信息
            ServletUtils.writeToResponse(response, ResponseWrapper.error("No user information was found."));
            return;
        }
        Boolean isSuccess = userBaseInfoService.basicInfoAuth(homeAddress, stateAddr, marital,
                accommodationType, occupation, salaryRange, educationalQualification, userId, childrenNumber, loanPurpose, salarySource);
        //保存个人信息失败
        if (!isSuccess) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("Basic information authentication failed."));
            return;
        }
        //更新认证项表/更新认证时间表
        paramMap.put("basicInfoState", "30");
        userAuthService.updateByUserId(paramMap);
        UserAuthTime userAuthTime = userAuthTimeService.getUserAuthTimeByUserId(Long.parseLong(userId));
        if (userAuthTime.getBasicInfoAuthOverTime() == null) {
            //首次认证
            userAuthTime.setBasicInfoAuthOverTime(new Date());
        } else {
            //非首次认证
            userAuthTime.setBasicInfoAuthUpdateTime(new Date());
        }
        userAuthTimeService.updateSelectiveByObject(userAuthTime);
        ServletUtils.writeToResponse(response, ResponseWrapper.success("Basic information certification was successful."));
    }

    /**
     * 根据type获取相应的字典项
     *
     * @param type 字典表类型
     */
    @RequestMapping(value = "/api/act/dict/list.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getDicts(@RequestParam(value = "type") String type) {
        Map<String, Object> dicList = new HashMap<>(8);
        if (type != null && !"".equals(type)) {
            String[] types = type.split(",");
            for (String type1 : types) {
                dicList.put(StringUtil.clearUnderline(type1) + "List", userBaseInfoService.getDictsCache(type1));
            }
        }
        if (!dicList.isEmpty()) {
            return JsonUtil.newJson().addData(Constant.RESPONSE_DATA, dicList).toJson().toJSONString();
        } else {
            return JsonUtil.newFailJson().toJson().toJSONString();
        }
    }


    /**
     * 获取认证状态
     *
     * @param userId 用户id
     */
    @RequestMapping(value = "/api/act/mine/userAuth/getUserAuth.htm", method = RequestMethod.GET)
    public void getUserAuth(@RequestParam(value = "userId") String userId) {
        Map<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("userId", Long.parseLong(userId));
        UserAuth userAuth = userAuthService.getUserAuth(paramMap);
        if (userAuth == null) {
            //未找到用户信息
            ServletUtils.writeToResponse(response, ResponseWrapper.error("No user information was found."));
            return;
        }
        ServletUtils.writeToResponse(response, ResponseWrapper.success("Successful access to user authentication information.", userAuth));
    }

}
