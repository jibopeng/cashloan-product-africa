package com.ajaya.cashloan.cl.service;

import java.util.List;
import java.util.Map;

import com.ajaya.cashloan.cl.domain.BorrowProgress;
import com.ajaya.cashloan.cl.model.ManageBorrowProgressModel;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.service.BaseService;
import com.ajaya.cashloan.core.domain.Borrow;

/**
 * 借款进度表Service
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @since 2020-02-28 10:31:04
 */
public interface BorrowProgressService extends BaseService<BorrowProgress, Long> {

    /**
     * 进度查询
     *
     * @param borrowId 借款id
     * @return Map<String,Object>
     */
    Map<String, Object> result(Borrow borrowId);

    /**
     * 后台还款进度列表
     *
     * @param params      参数map
     * @param currentPage 当前页
     * @param pageSize    当前页大小
     * @return 分页查询结果列表
     */
    Page<ManageBorrowProgressModel> listModel(Map<String, Object> params,
                                              int currentPage, int pageSize);

    /**
     * 保存借款进度
     *
     * @param borrowProgress 借款进度表列表
     * @return 是否成功
     */
    boolean save(BorrowProgress borrowProgress);

    /**
     * 查询列表
     *
     * @param map 参数map
     * @return 查询结果列表
     */
    List<BorrowProgress> listSeletetiv(Map<String, Object> map);

    /**
     * 查询当前借款逾期或坏账进度条数
     *
     * @param borrowId 借款id
     * @return 条数
     */
    int isNormalBorrowProgress(long borrowId);
    /**
     * 查询审批通过进度
     * @param borrowId 订单id
     * @return 进度实体
     */
    BorrowProgress selectSanctionDate(Long borrowId);
}
