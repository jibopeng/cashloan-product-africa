package com.ajaya.cashloan.api.controller;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.model.BankAccountCheck;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.util.ValidInfoUtils;
import com.ajaya.cashloan.core.domain.UserBaseInfo;
import com.ajaya.cashloan.core.service.UserBaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.service.BankAccountService;
import com.ajaya.cashloan.core.common.web.controller.BaseController;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明：银行账户以及认证相关业务
 *
 * @author yanzhiqiang
 * @since 2021-01-20 17:21
 */

@Controller
@Scope("prototype")
public class BankAccountController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BankAccountController.class);
    @Resource
    private BankAccountService bankAccountServiceImpl;

    @Resource
    private UserBaseInfoService userBaseInfoService;

    /**
     * 根据用户id 查找用户bvn号码
     *
     * @param userId 用户id
     */
    @RequestMapping(value = "/api/act/account/getBvnAccount.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void getBvnAccount(@RequestParam(value = "userId") Long userId) {
        UserBaseInfo info = userBaseInfoService.findByUserId(userId);
        if (info == null || info.getBvnNo() == null) {
            //未找到用户信息
            ServletUtils.writeToResponse(response, ResponseWrapper.error("No user information was found,or no have bvn account"));
            return;
        }
        String bvnNo = info.getBvnNo();
        Map<String, Object> res = new HashMap<>(8);
        res.put("bvnNo", bvnNo);
        ServletUtils.writeToResponse(response, ResponseWrapper.success(res));
    }

    /**
     * 调用payStack 获取银行列表
     *
     * @param perPage 每页size
     * @param page    页数
     * @param userId  用户id
     */
    @RequestMapping(value = "/api/act/account/getBankList.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void getBankList(@RequestParam(value = "userId") Long userId, @RequestParam(value = "perPage") Integer perPage,
                            @RequestParam(value = "page") Integer page) {
        ResponseWrapper responseWrapper = bankAccountServiceImpl.getBankList(perPage, page, userId);
        ServletUtils.writeToResponse(response, responseWrapper);
    }

    /**
     * 解析银行账户所属人名称
     *
     * @param userId        用户id
     * @param accountNumber 银行账户
     * @param bankCode      银行代码
     */
    @RequestMapping(value = "/api/act/account/resolveAccount.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void resolveAccount(@RequestParam(value = "userId") Long userId, @RequestParam(value = "accountNumber") String accountNumber,
                               @RequestParam(value = "bankCode") String bankCode) {
        ResponseWrapper responseWrapper = bankAccountServiceImpl.resolveAccount(accountNumber, bankCode, userId);
        ServletUtils.writeToResponse(response, responseWrapper);
    }


    /**
     * 银行卡绑定(PayStack)
     *
     * @param bankCode      银行卡代码
     * @param accountNumber 银行账号
     * @param userId        用户id
     */
    @RequestMapping(value = "/api/act/account/binding.htm", method = RequestMethod.POST)
    public void bankAccountBinding(
            @RequestParam(value = "bankCode") String bankCode,
            @RequestParam(value = "accountNumber") String accountNumber,
            @RequestParam(value = "bankName") String bankName,
            @RequestParam(value = "bvn") String bvn,
            @RequestParam(value = "holderName") String holderName,
            @RequestParam(value = "userId") Long userId) {
        bankCode = bankCode.trim();
        accountNumber = accountNumber.trim();
        bankName = bankName.trim();
        //参数是否为空
        if (StringUtil.isEmpty(bankCode.trim()) || StringUtil.isEmpty(accountNumber.trim()) || StringUtil.isEmpty(bankName.trim())
                || StringUtil.isEmpty(holderName.trim()) || StringUtil.isEmpty(bvn.trim())) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("The params cannot be empty."));
            return;
        }
        //bank account
        if (!ValidInfoUtils.isBankAccount(accountNumber)) {
            //pin 格式不正确
            ServletUtils.writeToResponse(response, ResponseWrapper.error("The format of the bank account is incorrect. Please try again."));
            return;
        }
        //验证是否为绑定过 如果已经绑定则返回绑定成功
        if (bankAccountServiceImpl.bound(bankCode, accountNumber, userId)) {
            ServletUtils.writeToResponse(response, ResponseWrapper.success("The bank details have successfully submitted."));
            return;
        }

        //bvn账户认证。
        BankAccountCheck bankAccountCheck = bankAccountServiceImpl.bankAccountAuth(bankCode, accountNumber, bankName, holderName, bvn, userId);
        if (!bankAccountCheck.isBankAccount()) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error(bankAccountCheck.getMessage()));
            return;
        }
        //绑定失败
        if (!bankAccountServiceImpl.binding(accountNumber, bankCode, userId)) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("Sorry,failed to submit,please try again."));
            return;
        }
        ServletUtils.writeToResponse(response, ResponseWrapper.success("The bank details have successfully submitted."));
    }


    /**
     * 解析银行账户信息
     *
     * @param userId        用户id
     */
    @RequestMapping(value = "/api/act/account/getAccountInfo.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void getAccountInfo(@RequestParam(value = "userId") Long userId) {
        ResponseWrapper responseWrapper = bankAccountServiceImpl.getAccountInfo( userId);
        ServletUtils.writeToResponse(response, responseWrapper);
    }
}
