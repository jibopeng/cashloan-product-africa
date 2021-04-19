package com.ajaya.cashloan.core.common.context;


/**
 * 公用常量类定义
 *
 * @author 吴国成f
 * @version 1.0
 * @created 2014年9月23日 下午2:17:20
 */
public class Constant {


    /**
     * 角色session 暂用
     */
    public static final String ROLEID = "roleId";

    public static final String INSERT = "create";

    public static final String UPDATE = "update";

    public static final String ACCESSCODE = "accessCode";

    public static final String RESPONSE_CODE = "code";

    public static final String RESPONSE_CODE_MSG = "msg";

    public static final String RESPONSE_DATA = "data";

    public static final String RESPONSE_DATA_TOTAL = "total";

    public static final String RESPONSE_DATA_TOTALCOUNT = "totalCount";

    public static final String RESPONSE_DATA_CURRENTPAGE = "currentPage";

    public static final String RESPONSE_DATA_PAGE = "page";


    public static final String OPERATION_SUCCESS = "操作成功";

    public static final String OPERATION_FAIL = "操作失败";

    public static final int SIGN_FAIL = 99; // 验签失败

    public static final int SUCCEED_CODE_VALUE = 200; // 成功 插入 、删除 更新 修改

    public static final int FAIL_CODE_VALUE = 400; // 失败 插入 、删除 更新
    public static final int PERM_CODE_VALUE = 403; // 无权限访问

    public static final int OTHER_CODE_VALUE = 500; // 其他异常

    public static final int NOSESSION_CODE_VALUE = 800; // session失效

    public static final int CLIENT_EXCEPTION_CODE_VALUE = 998; // 连接异常（除请求超时）

    public static final int TIMEOUT_CODE_VALUE = 999; // 请求超时


    /**
     * epoch 回调验签公钥
     */
    public static final String EPOCH_CALL_BACK_PUB_KEY = "epoch_call_back_pub_key";


    /**
     * 短信渠道标识 10 Talking
     */

    public static final String SMS_TALKING_FLAG = "10";
    /**
     * 收费
     */
    public static final String PAYSTACK_WEBHOOK_CHARGE = "charge.";
    /**
     * 交易
     */
    public static final String PAYSTACK_WEBHOOK_TRANSFER = "transfer.";
    /**
     * 放款订单前缀
     */
    public static final String PAYSTACK_WEBHOOK_REFERENCE_POT = "pot";
    /**
     * 还款订单前缀
     */
    public static final String PAYSTACK_WEBHOOK_REFERENCE_REP = "rep";
    /**
     * 绑卡订单前缀
     */
    public static final String PAYSTACK_WEBHOOK_REFERENCE_CAR = "car";


    /**
     * 线下支付状态 PAID 支付成功
     */
    public static final String TEAMAPT_TRANSACTIONS_STATUS_PAID = "PAID";

    /**
     * 会话状态
     */
    public static final String USER_VOICE_SESSON_STATUS = "Answered";
    /**
     * 会话状态回调方法
     */
    public static final String USER_VOICE_CALL_BACK = "callback";
    /**
     * 会话状态回调方法 event
     */
    public static final String USER_VOICE_EVENT= "event";

    /**
     * 放款响应失败报错
     */
    public static final String PAYSTACK_SERVER_EORRO_DESCRIPTION ="You cannot initiate third party payouts at this time";
}
