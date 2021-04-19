package com.ajaya.cashloan.cl.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.domain.*;
import com.ajaya.cashloan.cl.mapper.*;
import com.ajaya.cashloan.cl.model.*;
import com.ajaya.cashloan.cl.service.BankAccountService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.util.HttpClientUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;


/**
 * 银行账号信息表ServiceImpl
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-20 14:49:23
 */

@Service("bankAccountService")
public class BankAccountServiceImpl extends BaseServiceImpl<BankAccount, Long> implements BankAccountService {

    private static final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    @Resource
    private BankAccountMapper bankAccountMapper;
    @Resource
    private UserAuthMapper userAuthMapper;
    @Resource
    private UserAuthTimeMapper userAuthTimeMapper;
    @Resource
    private BankAccountLogMapper bankAccountLogMapper;
    @Resource
    private ClBorrowMapper clBorrowMapper;

    private static final String ACCOUNT_NUMBER_URL = "https://api.paystack.co/bvn/match";
    private static final String GET_BANK_LIST_URL = "https://api.paystack.co/bank";
    private static final String RESOLVE_ACCOUNT_URL = "https://api.paystack.co/bank/resolve";

    @Override
    public BaseMapper<BankAccount, Long> getMapper() {
        return bankAccountMapper;
    }


    @Override
    public boolean bound(String bankCode, String accountNumber, Long userId) {
        HashMap<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("accountNumber", accountNumber);
        paramMap.put("bankCode", bankCode);
        paramMap.put("userId", userId);
        paramMap.put("status", "1");
        BankAccount bankAccountAuth = bankAccountMapper.findSelective(paramMap);
        logger.info("用户 {} 请求验证银行账号是否已经绑定结果：{}", userId, bankAccountAuth != null);
        //如果对象为空则没有绑定
        return bankAccountAuth != null;
    }

    @Override
    public boolean binding(String accountNumber, String bankCode, Long userId) {

        //将该用户所有卡重置为未绑定状态
        HashMap<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("userId", userId);
        bankAccountMapper.setNoBinding(paramMap);
        paramMap.put("accountNumber", accountNumber);
        paramMap.put("bankCode", bankCode);
        paramMap.put("userId", userId);
        BankAccount selective = bankAccountMapper.findSelective(paramMap);
        if (selective == null) {
            return false;
        }
        selective.setStatus("1");
        selective.setUpdateTime(new Date());
        //更新该张银行账户为绑定状态
        int i = bankAccountMapper.update(selective);
        logger.info("用户 {} 请求验证银行账号经绑定结果：{}", userId, i > 0);

        //绑定成功后进行认证相更新
        if (i > 0) {
            //更新认证相信息
            HashMap<String, Object> hashMap = new HashMap<>(8);
            hashMap.put("userId", userId);
            UserAuth userAuth = userAuthMapper.findSelective(hashMap);
            userAuth.setBankAccountState("30");
            userAuthMapper.update(userAuth);
            //更新认证时间信息
            UserAuthTime userAuthTime = userAuthTimeMapper.findSelective(hashMap);
            if (userAuthTime.getBankAccountAuthOverTime() == null) {
                //第一次更新
                userAuthTime.setBankAccountAuthOverTime(new Date());
            } else {
                //非首次认证
                userAuthTime.setBankAccountAuthUpdateTime(new Date());
            }
            userAuthTimeMapper.update(userAuthTime);
            logger.info("用户 {} 更新认证项完成", userId);
        }
        return i > 0;
    }


    @Override
    public boolean setDefault(Long accountId) {
        BankAccount bankAccount = bankAccountMapper.findByPrimary(accountId);
        //将该用户所有卡重置为未绑定状态
        HashMap<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("userId", bankAccount.getUserId());
        bankAccountMapper.setNoBinding(paramMap);
        bankAccount.setStatus("1");
        bankAccount.setUpdateTime(new Date());
        //更新该张银行账户为绑定状态
        int i = bankAccountMapper.update(bankAccount);
        logger.info("用户 {} 银行卡设置默认选中结果：{}", bankAccount.getUserId(), i > 0);
        return i > 0;
    }

    @Override
    public BankAccountCheck bankAccountAuth(String bankCode, String accountNumber, String bankName, String holderName, String bvn, Long userId) {
        BankAccountCheck bankAccountCheck = new BankAccountCheck();
        bankAccountCheck.setBankAccount(false);
        HashMap<String, String> header2 = getHeader2();
        HashMap<String, String> param = new HashMap<>(1);
        param.put("bvn", bvn);
        param.put("account_number", accountNumber);
        param.put("bank_code", bankCode);
        String paramJson = JSONObject.toJSONString(param);

        //如果之前解析成功过则拿数据库信息，则拿数据库里的信息
        param.clear();
        param.put("userId", userId + "");
        param.put("param", paramJson);
        param.put("status", "true");
        BankAccountLog BankAccountLog = bankAccountLogMapper.findSelectiveNew(param);
        String result;
        boolean saveLog =false;
        if (BankAccountLog != null) {
            result = BankAccountLog.getResult();
        } else {
            result = HttpClientUtil.sendPost(ACCOUNT_NUMBER_URL, header2, paramJson, "utf-8");
            saveLog=true;
        }
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            Boolean status = resultObj.getBoolean("status");
            ///-----------------------BVN修改
            status=true;
            String message = resultObj.getString("message");
            //保存日志
            if (saveLog){
                saveLog(userId, paramJson, result, status, message);
            }
            if (status) {
                JSONObject data = resultObj.getJSONObject("data");
                Boolean isBlacklisted = true;
                Boolean account = true;
                //-----------------BVN注释
                /*Boolean isBlacklisted = data.getBoolean("is_blacklisted");
                Boolean account = data.getBoolean("account_number");
                Boolean bankCodeB = data.getBoolean("bank_code");*/
//                Boolean firstName = data.getBoolean("first_name");
//                Boolean middleName = data.getBoolean("middle_name");
//                Boolean lastName = data.getBoolean("last_name");


                //-------------------VBN注释
                /*if (bankCodeB!=null&&!bankCodeB){
                    bankAccountCheck.setMessage("The bank code is wrong, please try it again");
                    return bankAccountCheck;
                }
                //账号解析结果
                if (!account) {
                    bankAccountCheck.setMessage("The account number is wrong, please try it again");
                    return bankAccountCheck;
                }
                //黑名单解析
                if (isBlacklisted) {
                    bankAccountCheck.setMessage("This account number  is not supported, please use another account number");
                    return bankAccountCheck;
                }*/
                //姓名匹配结果
//                if (!firstName || !lastName) {
//                    bankAccountCheck.setMessage("Failed to match BVN and Response Number names.");
//                    return bankAccountCheck;
//                }

                //如果解析没问题保存记录
                bankAccountSave(bankCode, accountNumber, bankName, holderName, bvn, userId, account, isBlacklisted);
                bankAccountCheck.setBankAccount(true);
                return bankAccountCheck;
            }
            bankAccountCheck.setBankAccount(false);
            bankAccountCheck.setMessage(message);
            return bankAccountCheck;
        } else {
            logger.info("用户 {} 调用银行账号验证接口，返回空", userId);
            bankAccountCheck.setMessage("Response Number validation failed. Please try again later");
            return bankAccountCheck;
        }
    }

    @Override
    public ResponseWrapper getBankList(Integer perPage, Integer page, Long userId) {
        HashMap<String, String> header = getHeader();
        HashMap<String, String> param = new HashMap<>(2);
        param.put("perPage", Integer.toString(perPage));
        param.put("page", Integer.toString(page));
        String result = HttpClientUtil.sentGet(GET_BANK_LIST_URL, header, param, "utf-8");
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            Boolean status = resultObj.getBoolean("status");
            String message = resultObj.getString("message");
            if (status) {
                String data = resultObj.getString("data");
                List<BankModel> bankModelList = JSONArray.parseArray(data, BankModel.class);
                return ResponseWrapper.success(bankModelList);
            } else {
                logger.info("用户 {} 调用银行列表接口，返回 {}", message);
                return ResponseWrapper.error(message);
            }
        } else {
            logger.info("用户 {} 调用银行列表接口，返回空");
            return ResponseWrapper.error("System abnormal, please try again later.");
        }
    }

    @Override
    public ResponseWrapper resolveAccount(String accountNumber, String bankCode, Long userId) {
        HashMap<String, String> header = getHeader();
        HashMap<String, String> param = new HashMap<>(2);
        param.put("account_number", accountNumber);
        param.put("bank_code", bankCode);
        String result = HttpClientUtil.sentGet(RESOLVE_ACCOUNT_URL, header, param, "utf-8");
        if (result != null) {
            JSONObject resultObj = JSONObject.parseObject(result);
            Boolean status = resultObj.getBoolean("status");
            String message = resultObj.getString("message");
            //---------------------修改地方
            status=true;
            if (status) {
                JSONObject data = resultObj.getJSONObject("data");
                AccountModel accountModel = JSONObject.toJavaObject(data, AccountModel.class);
                return ResponseWrapper.success(accountModel);
            } else {
                logger.info("用户 {} 调用解析账户接口，返回 {}", userId, message);
                return ResponseWrapper.error(message);
            }
        } else {
            logger.info("用户 {} 调用解析账户接口，返回空", userId);
            return ResponseWrapper.error("System abnormal, please try again later.");
        }
    }

    @Override
    public ResponseWrapper getAccountInfo(Long userId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", userId);
        hashMap.put("status", "1");
        BankAccount bankAccount = bankAccountMapper.findSelective(hashMap);
        BankAccountModel bankAccountModel = new BankAccountModel();
        //默认可以进行绑卡操作

        if (bankAccount != null) {
            //默认可以进行绑卡
            bankAccountModel.setStatus("1");
            BeanUtils.copyProperties(bankAccount, bankAccountModel);
            Borrow borrow = clBorrowMapper.findLastBorrow(userId);
            if (borrow != null) {
                if (BorrowModel.STATE_AUTO_REFUSED.equals(borrow.getState()) || BorrowModel.STATE_REFUSED.equals(borrow.getState())
                        || BorrowModel.STATE_FINISH.equals(borrow.getState()) || BorrowModel.STATE_CANCEL.equals(borrow.getState())) {
                } else {
                    //有未完成的订单不让换卡
                    bankAccountModel.setStatus("2");
                }
            }
        }
        return ResponseWrapper.success(bankAccountModel);
    }


    private void bankAccountSave(String bankCode, String accountNumber, String bankName, String holderName, String bvn, Long userId, Boolean account, Boolean isBlacklisted) {

        //将该用户所有卡重置为未绑定状态
        HashMap<String, Object> paramMap = new HashMap<>(8);
        paramMap.put("userId", userId);
        paramMap.put("accountNumber", accountNumber);
        paramMap.put("bankCode", bankCode);
        BankAccount bankAccount = bankAccountMapper.findSelective(paramMap);
        boolean save = false;
        if (bankAccount == null) {
            bankAccount = new BankAccount();
            save = true;
            bankAccount.setCreateTime(new Date());
        }
        bankAccount.setAccount(Boolean.toString(account));
//        bankAccount.setFirstName(Boolean.toString(firstName));
//        bankAccount.setMiddleName(Boolean.toString(middleName));
//        bankAccount.setLastName(Boolean.toString(lastName));
        bankAccount.setIsBlacklisted(Boolean.toString(isBlacklisted));
        bankAccount.setUserId(userId);
        bankAccount.setBvn(bvn);
        bankAccount.setBankCode(bankCode);
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setBankName(bankName);
        bankAccount.setHolderName(holderName);
        if (save) {
            bankAccountMapper.save(bankAccount);
        } else {
            bankAccount.setUpdateTime(new Date());
            bankAccountMapper.update(bankAccount);
        }
    }

    private void saveLog(Long userId, String bvnNumber, String result, Boolean status, String message) {
        BankAccountLog bankAccountLog = new BankAccountLog();
        bankAccountLog.setCreateTime(new Date());
        bankAccountLog.setMessage(message);
        bankAccountLog.setStatus(Boolean.toString(status));
        bankAccountLog.setParam(bvnNumber);
        bankAccountLog.setUserId(userId);
        bankAccountLog.setResult(result);
        bankAccountLogMapper.save(bankAccountLog);
    }

    private HashMap<String, String> getHeader() {
        HashMap<String, String> header = new HashMap<>(1);
        header.put("Authorization", Global.getValue("pay_stack_secret_key"));
        return header;
    }

    private HashMap<String, String> getHeader2() {
        HashMap<String, String> header = new HashMap<>(1);
        header.put("Authorization", Global.getValue("pay_stack_secret_key"));
        header.put("Content-Type", "application/json");
        return header;
    }

}