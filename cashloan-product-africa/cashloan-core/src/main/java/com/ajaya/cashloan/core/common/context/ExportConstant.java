package com.ajaya.cashloan.core.common.context;

/**
 *
 * 后台列表数据导出 常量
 * @author lyang
 * @date 2017年4月13日 下午14:40:52
 */
public final class ExportConstant {

	/** 导出暂定为5000条，如有需求，酌情更改*/
	/*** 分页起始*/
	public static final int STRAT_LIMIT = 0;

	/*** 分页结束*/
	public static final int END_LIMIT = 5000;

	/** 还款记录导出 表头*/
	public static final String[] EXPORT_REPAYLOG_LIST_HEARDERS = {"真实姓名","手机号码","渠道名称","订单号", "借款金额(Rs)","综合费用(Rs)","应还金额(Rs)","逾期天数","应还逾期罚金(Rs)","应还总额(Rs)","实还金额(Rs)", "实还逾期罚金(Rs)",  "实还总金额(Rs)'",
			"还款账号", "流水号", "还款方式", "应还款日期", "实际还款日期","支付渠道"};
	/** 还款记录导出 属性数组*/
	public static final String[] EXPORT_REPAYLOG_LIST_FIELDS = {"realName","phone","channelName","orderNo", "borrowAmount","fee","repayAmount", "penaltyDay","repayPenalty","repayTotal","repayLogAmount","penaltyAmout", "repayYesTotal",
			"repayAccount", "serialNumber", "repayWay", "repayPlanTime", "repayTime","payChannel"};

	/** 借款订单导出 表头*/
	public static final String[] EXPORT_BORROW_LIST_HEARDERS = {"真实姓名","手机号","订单号","借款金额(Rs)","借款期限","订单生成时间","综合费用",
			"实际到账金额","订单状态","放款时间","实际还款时间","实际还款金额(Rs)","逾期天数","逾期罚金(Rs)"};
	/** 借款订单导出 属性数组*/
	public static final String[] EXPORT_BORROW_LIST_FIELDS = {"realName", "phone", "orderNo", "amount","timeLimit","createTime", "fee",
			"realAmount", "state", "loanTime", "repayTime", "repayAmount","penaltyDay","penaltyAmout"};

	/** 支付记录导出 表头*/
	public static final String[] EXPORT_PAYLOG_LIST_HEARDERS = {"收款人姓名","手机号码","订单号","支付单号","金额","收款银行卡","借款时间","打款时间","业务","状态","支付渠道"};
	/** 支付记录导出 属性数组*/
	public static final String[] EXPORT_PAYLOG_LIST_FIELDS = {"realName","loginName","borrowOrderNo","tradeNo","amount","cardNo","loanTime","updateTime","scenesManageStr","stateManageStr","payChannel"};

	/** 支付对账记录导出 表头*/
	public static final String[] EXPORT_PAYCHECK_LIST_HEARDERS = {"订单号","支付方式","订单金额","支付方订单金额","错误类型","对账记录添加时间","支付业务","处理方式","处理结果"};
	/** 支付对账记录导出 属性*/
	public static final String[] EXPORT_PAYCHECK_LIST_FIELDS = {"orderNo","payTypeStr","orderAmount","realPayAmount","typeStr","processTime",
			"scenesStr","processWayStr","processResultStr",};

	/** 已逾期订单导出 表头*/
	public static final String[] EXPORT_OVERDUE_LIST_HEARDERS = {"真实姓名","手机号","渠道名称","订单号","借款金额(Rs)","借款期限(天)","订单生成时间","综合费用(Rs)","居间服务费(Rs)","信息认证费(Rs)","利息(Rs)",
			"实际到账金额","订单状态","借款地址","放款时间","逾期天数","逾期罚金(Rs)","逾期等级"};
	/** 已逾期订单导出 属性数组*/
	public static final String[] EXPORT_OVERDUE_LIST_FIELDS = {"realName", "phone","channelName", "orderNo", "amount","timeLimit","createTime", "fee",
			"serviceFee","infoAuthFee","interest","realAmount", "state", "address", "loanTime","penaltyDay","penaltyAmout","level"};

	/** 已坏账订单导出 表头*/
	public static final String[] EXPORT_BADDEBT_LIST_HEARDERS = {"真实姓名","手机号","订单号","借款金额(Rs)","借款期限","订单生成时间","综合费用","居间服务费","信息认证费","利息",
			"实际到账金额","订单状态","借款地址","放款时间","实际还款时间","实际还款金额(Rs)","逾期天数","逾期罚金(Rs)"};
	/** 已坏账订单导出 属性数组*/
	public static final String[] EXPORT_BADDEBT_LIST_FIELDS = {"realName", "phone", "orderNo", "amount","timeLimit","createTime", "fee",
			"serviceFee","infoAuthFee","interest","realAmount", "state", "address", "loanTime", "repayTime", "repayAmount","penaltyDay","penaltyAmout"};

	/** 催收订单导出 表头*/
	public static final String[] EXPORT_REPAYORDER_LIST_HEARDERS = {"loanId","language","flag","name","phone","amount","repayAmount","realRepayTime","borrowTime","repayTime","penaltyDay","level","penaltyAmout","collection Name","state"};
	/** 催收订单导出 属性*/
	public static final String[] EXPORT_REPAYORDER_LIST_FIELDS = {"orderNo","language","borrowFlag","borrowName","phone","amount","repayAmount","realRepayTime","borrowTime","repayTime","penaltyDay","level",
			"penaltyAmout","userName","stateStr"};

	/** 催收反馈导出 表头*/
	public static final String[] EXPORT_URGELOG_LIST_HEARDERS = {"借款人姓名","手机号码","金额","借款时间","预计还款时间","逾期天数","逾期等级","罚息","催收人","订单状态",
			"催收方式","承诺还款时间","催收反馈","催收时间"};
	/** 催收反馈导出 属性*/
	public static final String[] EXPORT_URGELOG_LIST_FIELDS = {"borrowName","phone","amount","borrowTime","repayTime","penaltyDay","level",
			"penaltyAmout","userName","state","way","promiseTime","remark","createTime"};

	/** 客户基本信息导出*/
	public static final String[] EXPORT_USERBASEMESSAGE_LIST_HEARDERS = {"用户ID","订单号","导出时间","借款时间","借款金额","实际应还金额","应还款日期",
			"服务费","逾期天数","滞纳金","姓名","性别","身份证号","手机号","居住地址","居住详细地址","受教育情况","公司名称","公司地址","公司详细地址"};
	/** 客户基本信息导出属性*/
	public static final String[] EXPORT_USERBASEMESSAGE_LIST_FIELDS = {"user_id","order_no","sysdate","create_time","amount","sumamount","repay_time"
			,"fee","penalty_day","penalty_amout","real_name","sex","id_no","phone","live_addr","live_detail_addr","education","company_name","company_addr"
			,"company_detail_addr"};
	/** 客户通讯录表头*/
	public static final String[] EXPORT_USERCONNACTLIST_HEARDERS = {"用户ID","借款用户姓名","联系人姓名","手机号"};
	/** 客户通话记录表头*/
	public static final String[] EXPORT_USERVOICESLIST_HEARDERS = {"用户ID","借款用户姓名","通话手机号","通话时间（秒）","通话类型","通话时长"};

	/** 用户新贷还款表现表头 */
	public static final String [] EXPORT_NEWLOANREPPERLIST_HEADERS = {"新贷应还款日期","新贷应还款总数","新贷已还款","当日还款当日复贷放款数","当日复贷率","30天复贷放款数","30天复贷率","新贷未还款",
			"新贷未还款占比","新贷已还款占比","不逾期占比","逾期件数","逾期件数占比","逾期D1","逾期D1占比","逾期D2","逾期D2占比","逾期D3","逾期D3占比","逾期D4","逾期D4占比","逾期D5","逾期D5占比","逾期D6",
			"逾期D6占比","逾期D7","逾期D7占比","逾期D10","逾期D10占比","逾期D14","逾期D14占比"};

	/** 用户新贷还款表现导出属性 */
	public static final String [] EXPORT_NEWLOANREPPERLIST_FIELDS = {"repaymentTime","quantityRepayment","quantityAlRepayment","quantityRepOldLoan","ratioOldLoan","quantity30DaysOldLoan","ratio30OldLoan","quantityUnrepayment",
			"ratioUnrepayment","ratioAlrepayment","ratioNotOverdue","overdueLoan","ratioOverdue","quantityOverdueDay1","ratioOverdueDay1","quantityOverdueDay2","ratioOverdueDay2","quantityOverdueDay3","ratioOverdueDay3","quantityOverdueDay4","ratioOverdueDay4","quantityOverdueDay5","ratioOverdueDay5","quantityOverdueDay6",
			"ratioOverdueDay6","quantityOverdueDay7","ratioOverdueDay7","quantityOverdueDay10","ratioOverdueDay10","quantityOverdueDay14","ratioOverdueDay14"};

	/** 用户复贷还款表现表头 */
	public static final String [] EXPORT_OLDLOANREPPERLIST_HEADERS = {"复贷应还款日期","复贷应还款总数","复贷已还款","当日还款当日复贷放款数","当日复贷率","30天复贷放款数","30天复贷率","复贷未还款",
			"复贷未还款占比","复贷已还款占比","不逾期占比","逾期件数","逾期件数占比","逾期D1","逾期D1占比","逾期D2","逾期D2占比","逾期D3","逾期D3占比","逾期D4","逾期D4占比","逾期D5","逾期D5占比","逾期D6",
			"逾期D6占比","逾期D7","逾期D7占比","逾期D10","逾期D10占比","逾期D14","逾期D14占比"};

	/** 用户复贷还款表现导出属性 */
	public static final String [] EXPORT_OLDLOANREPPERLIST_FIELDS = {"repaymentTime","quantityRepayment","quantityAlRepayment","quantityRepOldLoan","ratioOldLoan","quantity30DaysOldLoan","ratio30OldLoan","quantityUnrepayment",
			"ratioUnrepayment","ratioAlrepayment","ratioNotOverdue","overdueLoan","ratioOverdue","quantityOverdueDay1","ratioOverdueDay1","quantityOverdueDay2","ratioOverdueDay2","quantityOverdueDay3","ratioOverdueDay3","quantityOverdueDay4","ratioOverdueDay4","quantityOverdueDay5","ratioOverdueDay5","quantityOverdueDay6",
			"ratioOverdueDay6","quantityOverdueDay7","ratioOverdueDay7","quantityOverdueDay10","ratioOverdueDay10","quantityOverdueDay14","ratioOverdueDay14"};

	/** 各流程转化率表头 */
	public static final String [] EXPORT_LOANVERIFYLIST_HEADERS = {"日期","总注册","实名认证","实名认证/注册数","个人信息认证","个人信息认证/实名认证","紧急联系人认证","紧急联系人认证/个人信息认证","银行卡认证"};

	/** 各流程转化率导出属性 */
	public static final String [] EXPORT_LOANVERIFYLIST_FIELDS = {"registerTime","quantityRegister","quantityVerified","ratioVerified","quantityBaseInfo","ratioBaseInfo","quantityEmergencyPerson","ratioEmergencyPerson","quantityBankCard"};
	
	
	/** 信审订单导出 表头*/
	public static final String[] EXPORT_XINSHEN_LIST_HEARDERS = {"信审员姓名","借款人姓名","借款人手机号","订单号","借款金额","借款期限","语言","新复贷标志","备注",
			"订单创建时间","订单入审时间","订单审核时间","订单状态"};
	/** 信审订单导出 属性*/
	public static final String[] EXPORT_XINSHEN_LIST_FIELDS = {"userName","borrowName","phone","orderNo","amount","timeLimit","language","borrowFlag","remark",
			"borrowCreateTime","borrowCreditTime","borrowApproveTime","stateStr"};
}
