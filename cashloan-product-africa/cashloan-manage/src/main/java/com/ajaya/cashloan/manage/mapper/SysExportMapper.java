package com.ajaya.cashloan.manage.mapper;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;
import com.ajaya.cashloan.manage.domain.SysExport;

/**
 * 导出统计表Dao
 * 
 * @author yinghh
 * @version 1.0.0
 * @date 2017-11-11 23:44:56

 */
@RDBatisDao
public interface SysExportMapper extends BaseMapper<SysExport, Long> {

	/**
	 * 导出逾期excel
	 * @param overDueSql
	 */
	List<Map<String, Object>> exportOverDue(String overDueSql);

	/**
	 * 获取每日数据【注册量】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayRegister(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【用户申请认证数】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayApplyAuth(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【用户认证完成数】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayApplyAuthOver(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【提审数(新贷总数量)】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayNewBorrowTotal(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【新贷(成功放款的)】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayNewMoney(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【续贷提审数】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayRefinanceBorrow(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【当日还款当日续贷】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayReturnMoneyAndRefinance(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【续贷放款】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayRefinanceMoney(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【日活】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayActive(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【总提审数】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayborrowAll(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【总机审通过数】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayJiShenAll(Map<String, Object> paramMap);

	/**
	 * 获取每日数据 【总放款件数】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayMoneyCountAll(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【总金额】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectDayMoneyAll(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【申请 500元7天】
	 * @param paramMap
	 * @return
	 */
	Long selectDayBorrow500And7(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【放款 500元7天】
	 * @param paramMap
	 * @return
	 */
	Long selectDayMoney500And7(Map<String, Object> paramMap);

	/**
	 *  获取每日数据【申请 500元14天】
	 * @param paramMap
	 * @return
	 */
	Long selectDayBorrow500And14(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【放款 500元14天】
	 * @param paramMap
	 * @return
	 */
	Long selectDayMoney500And14(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【申请 1000元7天】
	 * @param paramMap
	 * @return
	 */
	Long selectDayBorrow1000And7(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【放款 1000元7天】
	 * @param paramMap
	 * @return
	 */
	Long selectDayMoney1000And7(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【申请 1000元14天】
	 * @param paramMap
	 * @return
	 */
	Long selectDayBorrow1000And14(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【放款 1000元14天】
	 * @param paramMap
	 * @return
	 */
	Long selectDayMoney1000And14(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【总放款笔数】
	 * @param paramMap
	 * @return
	 */
	Long selectDayTotalAllMoneyCount(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【新贷总笔数】
	 * @param paramMap
	 * @return
	 */
	Long selectDayNewBorrowMoney(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【续贷N笔数】
	 * @param paramMap
	 * @return
	 */
	Long selectDayXuDaiBorrowMoney(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【续贷总金额】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectXudaiMoneyAll(Map<String, Object> paramMap);

	/**
	 * 获取每日数据【还款总笔数和总金额】
	 * @param paramMap
	 * @return
	 */
	List<Map<String, Object>> selectRepayMentAllNumAndMoney(Map<String, Object> paramMap);

}
