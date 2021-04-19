package com.ajaya.cashloan.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.core.common.util.ValidInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.domain.UserAuthTime;
import com.ajaya.cashloan.cl.domain.UserEmerContacts;
import com.ajaya.cashloan.cl.service.UserAuthService;
import com.ajaya.cashloan.cl.service.UserAuthTimeService;
import com.ajaya.cashloan.cl.service.UserEmerContactsService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.service.UserBaseInfoService;


/**
 * 紧急联系人Controller
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-02-14 11:24:05
 */
@Scope("prototype")
@Controller
public class UserEmerContactsController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(UserEmerContactsController.class);

    @Resource
    private UserBaseInfoService userBaseInfoService;
    @Resource
    private UserEmerContactsService userEmerContactsService;
    @Resource
    private UserAuthService userAuthService;

    @Resource
    private UserAuthTimeService userAuthTimeService;


    /**
     * 保存联系人
     *
     * @param name     联系人名称
     * @param phone    电话号
     * @param relation 关系
     * @param type     类型
     */
    @RequestMapping(value = "/api/act/mine/contact/saveOrUpdate.htm", method = RequestMethod.POST)
    public void saveOrUpdate(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "relation") String relation,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "userId", required = false) String userIdStr) {
        long userId = Long.parseLong(userIdStr);
        UserBaseInfo userBaseInfo = userBaseInfoService.findByUserId(userId);
        Map<String, Object> result = new HashMap<>(8);
        result.put("userId", userId);
        List<UserEmerContacts> contacts = userEmerContactsService.getUserEmerContactsByUserId(result);
        result.clear();
        logger.info("保存紧急联系人电话 " + phone + " 姓名 " + name);
        String[] names = name.split(",");
        String[] phones = phone.split(",");
        //判断app传入的是否是两个联系人
        if (names.length < 2 || phones.length < 2) {
            //说明app传入的数据有误
            result.put(Constant.RESPONSE_CODE_MSG, "Save failed");
            ServletUtils.writeToResponse(response, result);
            return;
        }
        //姓名判断 不为空
        if (StringUtil.isEmpty(names[0]) || StringUtil.isEmpty(names[1])) {
            //说明app传入的数据有误
            result.put(Constant.RESPONSE_CODE_MSG, "Name Format error");
            ServletUtils.writeToResponse(response, result);
            return;
        }
        //判断手机号
        int phoneCount = 0;
        for (String phone1 : phones) {
            phone1 = phone1.replace("-","").replace(" ","");
            if (!StringUtil.isPhone(phone1)) {
                result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                //手机号不正确
                result.put(Constant.RESPONSE_CODE_MSG, "Invalid Phone Number");
                ServletUtils.writeToResponse(response, result);
                return;
            }
            if (phone1.contains(userBaseInfo.getPhone())) {
                result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                //紧急联系人号码不能和自己的号码一致
                result.put(Constant.RESPONSE_CODE_MSG, "Emergency contact number can not be consistent with your own number.");
                ServletUtils.writeToResponse(response, result);
                return;
            }

            phoneCount++;
        }
        if (phoneCount >= 2) {
            if (phones[0].equals(phones[1])) {
                result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                //请勿选择两个一样的手机号码
                result.put(Constant.RESPONSE_CODE_MSG, "Do not choose two identical cell phone numbers.");
                ServletUtils.writeToResponse(response, result);
                return;
            }
        } else {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            //手机号码格式有误
            result.put(Constant.RESPONSE_CODE_MSG, "The format of mobile number is incorrect.");
            ServletUtils.writeToResponse(response, result);
            return;
        }
        //判断手机号结束

        String[] relations = relation.split(",");
        String[] types = type.split(",");
        int count = 0;
        if (contacts.size() == 0) {
            //新增
            for (int i = 0; i < names.length; i++) {
                UserEmerContacts info = new UserEmerContacts();
                info.setName(names[i]);
                info.setPhone(phones[i]);
                info.setRelation(relations[i]);
                info.setType(types[i]);
                info.setUserId(userId);
                userEmerContactsService.insert(info);
            }
            result.put("contactState", "30");
            result.put("userId", userId);
            userAuthService.updateByUserId(result);
            //刘晓亮 start
            logger.info("用户 {} 认证记录表，紧急联系人认证时间", userId);
            UserAuthTime userAuthTime = userAuthTimeService.getUserAuthTimeByUserId(userId);
            if (userAuthTime != null) {
                if (userAuthTime.getContactAuthOverTime() == null) {
                    //说明是第一次认证时间
                    userAuthTime.setContactAuthOverTime(new Date());
                    logger.info("用户 {} 认证记录表， 紧急联系人认证第一次完成时间！", userId);
                } else {
                    //说明是第二次
                    userAuthTime.setContactAuthUpdateTime(new Date());
                    logger.info("用户 {} 认证记录表，紧急联系人认证更新时间！", userId);
                }
                userAuthTimeService.updateSelectiveByObject(userAuthTime);
            }
        } else {
            //更新
            for (int i = 0; i < names.length && i < contacts.size(); i++) {
                if (contacts.get(i).getType().equals(types[i])) {
                    UserEmerContacts info = contacts.get(i);
                    info.setName(names[i]);
                    info.setPhone(phones[i]);
                    info.setRelation(relations[i]);
                    info.setType(types[i]);
                    count = userEmerContactsService.updateById(info);
                } else {
                    int j = i == 0 ? 1 : 0;
                    UserEmerContacts info = contacts.get(i);
                    info.setName(names[j]);
                    info.setPhone(phones[j]);
                    info.setRelation(relations[j]);
                    info.setType(types[j]);
                    count = userEmerContactsService.updateById(info);
                }
            }
            if (count > 0) {
                result.put("contactState", "30");
                result.put("userId", userId);
                userAuthService.updateByUserId(result);
                logger.info("用户 {} 认证记录表，紧急联系人认证时间", userId);
                UserAuthTime userAuthTime = userAuthTimeService.getUserAuthTimeByUserId(userId);
                if (userAuthTime != null) {
                    if (userAuthTime.getContactAuthOverTime() == null) {
                        //说明是第一次认证时间
                        userAuthTime.setContactAuthOverTime(new Date());
                        logger.info("用户 {} 认证记录表，紧急联系人认证第一次完成时间", userId);
                    } else {
                        //说明是第二次
                        userAuthTime.setContactAuthUpdateTime(new Date());
                        logger.info("用户 {} 认证记录表，紧急联系人认证更新时间", userId);
                    }
                    userAuthTimeService.updateSelectiveByObject(userAuthTime);
                }
            }
        }
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "Successfully Completed.");
        ServletUtils.writeToResponse(response, result);
    }
}
