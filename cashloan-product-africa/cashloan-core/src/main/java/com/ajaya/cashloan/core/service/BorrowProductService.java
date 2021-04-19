package com.ajaya.cashloan.core.service;


import com.ajaya.cashloan.core.domain.BorrowProduct;
import com.ajaya.cashloan.core.common.service.BaseService;

import java.util.List;

/**
 * '产品信息配置表Service
 * 
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2021-01-22 10:50:37
 */
public interface BorrowProductService extends BaseService<BorrowProduct, Long> {
    /**
     * 查找产品列表
     * @return 列表
     */
    List<BorrowProduct> findProductList();

}
