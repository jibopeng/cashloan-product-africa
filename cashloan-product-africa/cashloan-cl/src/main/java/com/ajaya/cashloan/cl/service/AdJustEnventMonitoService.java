package com.ajaya.cashloan.cl.service;

        import com.ajaya.cashloan.core.domain.Borrow;

/**
 * 功能说明：用户adjust 事件监控
 *
 * @author yanzhiqiang
 * @since 2020-07-16 22:20
 **/
public interface AdJustEnventMonitoService {
    /**
     * 统计事件
     * @param borrow 订单详情
     * @param type 事件类型  2申请3 申请通过 4放款
     */
    void adjustEnventMonito(Borrow borrow, int type);
}
