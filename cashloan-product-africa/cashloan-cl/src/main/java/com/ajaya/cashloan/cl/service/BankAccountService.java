package com.ajaya.cashloan.cl.service;


import com.ajaya.cashloan.cl.domain.BankAccount;
import com.ajaya.cashloan.cl.model.BankAccountCheck;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.core.common.service.BaseService;


/**
 * 银行账号信息表Service
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-20 14:49:23
 */
public interface BankAccountService extends BaseService<BankAccount, Long> {

    /**
     * 银行卡绑定
     *
     * @param accountNumber 账户id
     * @param userId        用户id
     * @param bankCode      银行code
     * @return 绑定是否成功标识
     */
    boolean binding(String accountNumber, String bankCode, Long userId);

    /**
     * 银行卡是否已经绑定
     *
     * @param bankCode      银行编码
     * @param accountNumber 账户号码
     * @param userId        用户id
     * @return 绑定是否成功标识
     */
    boolean bound(String bankCode, String accountNumber, Long userId);


    /**
     * 银行卡绑定
     *
     * @param accountId 银行账户id
     * @return 绑定是否成功标识
     */
    boolean setDefault(Long accountId);

    /**
     * @param bankCode      银行code
     * @param accountNumber 账户号码
     * @param bankName      银行名称
     * @param holderName    账户持有姓名
     * @param bvn           bvn账号
     * @param userId        用户id
     * @return 验证是否成功
     */

    BankAccountCheck bankAccountAuth(String bankCode, String accountNumber, String bankName, String holderName, String bvn, Long userId);

    /**
     * 查找银行列表
     *
     * @param perPage size
     * @param page    第几页
     * @param userId  用户id
     * @return 响应
     */
    ResponseWrapper getBankList(Integer perPage, Integer page, Long userId);

    /**
     * 根据银行code和账号解析账号持有人姓名
     *
     * @param accountNumber 账号
     * @param bankCode      银行code
     * @param userId        用户id
     * @return 响应
     */
    ResponseWrapper resolveAccount(String accountNumber, String bankCode,Long userId);

    /**
     * 获取银行发展话
     * @param userId 用户id
     * @return ResponseWrapper
     */
    ResponseWrapper getAccountInfo(Long userId);
}
