package com.ajaya.cashloan.cl.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.BorrowProgress;
import com.ajaya.cashloan.cl.model.BorrowProgressModel;
import com.ajaya.cashloan.cl.model.ManageBorrowProgressModel;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.mapper.RDBatisDao;

/**
 * 借款进度表Dao
 *
 * @author yanzhiqiang
 * @since 2017-02-14 10:31:04
 */
@RDBatisDao
public interface BorrowProgressMapper extends BaseMapper<BorrowProgress, Long> {

    /**
     * 首页查询进度
     *
     * @param bpMap 查找条件
     * @return 进度列表
     */
    List<BorrowProgressModel> listIndex(Map<String, Object> bpMap);

    /**
     * 后台借款进度列表
     *
     * @param params 参数map
     * @return 查询列表
     */
    List<ManageBorrowProgressModel> listModel(Map<String, Object> params);

    /**
     * 借款进度查询
     *
     * @param bpMap 参数map
     * @return 查询列表
     */
    List<BorrowProgressModel> listProgress(Map<String, Object> bpMap);


   /** 借款进度查询
     * @param bpMap 参数map
     * @return 查询列表
     */
    List<BorrowProgressModel> allProgress(Map<String, Object> bpMap);



    /**
     * 查询借款进度是否有逾期和坏账进度数
     *
     * @param borrowId 借款id
     * @return 坏账或借款逾期记录条数
     */
    int isNormalBorrowProgress(long borrowId);


    /**
     * 查询审批通过进度
     * @param borrowId 订单id
     * @return 进度实体
     */
    BorrowProgress selectSanctionDate(Long borrowId);

    /**
     * 根据borrowId 查找到用户的借款最后进度
     * @param borrowId 订单id
     * @return 最后进度
     */

    BorrowProgress findLastProcess(Long borrowId);

}
