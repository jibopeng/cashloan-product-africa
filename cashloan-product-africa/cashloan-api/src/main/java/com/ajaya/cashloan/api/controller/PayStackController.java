package com.ajaya.cashloan.api.controller;

import com.ajaya.cashloan.cl.model.PayLogModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.BankCardService;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.PayStackService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * 功能说明：PayStack支付功能
 *
 * @author yanzhiqiang
 * @since 2020-06-11 15:19
 **/
@Controller
@Scope("prototype")
public class PayStackController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PayStackController.class);

    @Resource
    private PayStackService payStackServiceImpl;
    @Resource
    private ClBorrowService clBorrowServiceImpl;
    @Resource
    private BankCardService bankCardServiceImpl;
    /**
     * PayStack WebHook call back
     *
     * @param payOutDetail 回调信息
     */
    @RequestMapping("/payStack/Payout/callBack.htm")
    public void payOutCallBack(@RequestBody JSONObject payOutDetail) {
        try {
            //回调延时处理
            System.out.println(payOutDetail.toString());
            Thread.sleep(2000);
            String event = payOutDetail.getString("event");
            JSONObject data = payOutDetail.getJSONObject("data");
            String reference = data.getString("reference");
            //绑卡、还款
            if (event.contains(Constant.PAYSTACK_WEBHOOK_CHARGE)) {
                if (reference.contains(Constant.PAYSTACK_WEBHOOK_REFERENCE_CAR)){
                    //绑卡
                    bankCardServiceImpl.dealCallBack(data);
                } else if (reference.contains(Constant.PAYSTACK_WEBHOOK_REFERENCE_REP)){
                    //还款
                    payStackServiceImpl.dealRepaymentCallBack(data,event);
                }else {
                    //未发现业务
                    logger.info("没有发现相关业务WebHook回调结果 {}",payOutDetail.toJSONString());
                }
            }else if (event.contains(Constant.PAYSTACK_WEBHOOK_TRANSFER)){
                //放款
                if (reference.contains(Constant.PAYSTACK_WEBHOOK_REFERENCE_POT))
                payStackServiceImpl.dealPayOutCallBack(data,event);
            }else {
                //未发现业务
                logger.info("没有发现相关业务WebHook回调结果 {}",payOutDetail.toJSONString());
            }
            response.getWriter().write(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/api/act/borrow/payOut.htm", method = RequestMethod.POST)
    public void payOut(@RequestParam(value = "borrowId") Long borrowId,  @RequestParam(value = "userId") Long userId){
        Borrow borrow = clBorrowServiceImpl.getById(borrowId);
        //不是放款状态
        if (!(BorrowModel.STATE_AUTO_PASS.equals(borrow.getState()) || BorrowModel.STATE_PASS.equals(borrow.getState()))) {
            ServletUtils.writeToResponse(response, ResponseWrapper.error("Order status does not belong to the pay out status."));
            return;
        }
        ResponseWrapper responseWrapper= payStackServiceImpl.payout(userId,borrowId);
        ServletUtils.writeToResponse(response, responseWrapper);
    }

    /**
     * 银行卡还款
     * @param borrowId 订单id
     * @param cardId
     */
    @RequestMapping(value = "/api/act/borrow/bankCardRepay.htm", method = RequestMethod.POST)
    public void bankCardRepay(@RequestParam(value = "borrowId") Long borrowId, @RequestParam(value = "cardId") Long cardId) {

        //银行卡转账权限校验
//        boolean check = payStackServiceImpl.checkAuthorization(cardId, borrowId);
//        if (!check) {
//            ServletUtils.writeToResponse(response, ResponseWrapper.error("Transaction failed,  Please change bank card."));
//            return;
//        }
        ResponseWrapper responseWrapper = payStackServiceImpl.bankCardRepay(cardId, borrowId, PayLogModel.TYPE_COLLECT);
        ServletUtils.writeToResponse(response, responseWrapper);
    }

}
