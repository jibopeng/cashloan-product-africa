package com.ajaya.cashloan.api.controller;


import com.ajaya.cashloan.cl.model.BankCardModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.BankCardService;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能说明：银行卡认证相关业务
 *
 * @author yanzhiqiang
 * @since 2021-01-20 17:21
 */

@Controller
@Scope("prototype")
public class BankCardController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(BankCardController.class);


    @Resource
    private BankCardService bankCardServiceImpl;
    /**
     * 生成用户授权码
     * @param userId 用户id
     * @param borrowId 订单id
     * @param type  1 首页触发 2 用户中心触发
     */
    @RequestMapping(value = "/api/act/bankCard/getAccessCode.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void getAccessCode(@RequestParam(value = "userId") Long userId ,@RequestParam(value = "type")String type
            ,@RequestParam(value = "borrowId" ,required = false)Long borrowId) {
        ResponseWrapper responseWrapper = bankCardServiceImpl.getAccessCode(userId,borrowId,type);
        ServletUtils.writeToResponse(response,responseWrapper);
    }

    /**
     * 生成用户授权码
     * @param userId 用户id
     * @param reference 绑卡交易唯一标识
     */
    @RequestMapping(value = "/api/act/bankCard/tradingCheck.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void tradingCheck(@RequestParam(value = "reference") String reference ,@RequestParam(value = "userId" )Long userId) {
        ResponseWrapper responseWrapper = bankCardServiceImpl.tradingCheck(userId,reference);
        ServletUtils.writeToResponse(response,responseWrapper);
    }

    /**
     * 根据用户id查询用户列表
     * @param userId 用户id
     */
    @RequestMapping(value = "/api/act/bankCard/getBankCardList.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void getBankCardList(@RequestParam(value = "userId" )Long userId) {
        List<BankCardModel> list = bankCardServiceImpl.getBankCardList(userId);
        ServletUtils.writeToResponse(response,ResponseWrapper.success(list));
    }


    /**
     * 根据用户id查询用户列表
     * @param userId 用户id
     * @param cardId 卡id
     */
    @RequestMapping(value = "/api/act/bankCard/setDefault.htm", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public void setDefault(@RequestParam(value = "userId" )Long userId,@RequestParam(value = "cardId" )Long cardId) {
        bankCardServiceImpl.setDefault(userId,cardId);
        ServletUtils.writeToResponse(response,ResponseWrapper.success());
    }

}
