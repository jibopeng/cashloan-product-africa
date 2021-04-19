package com.ajaya.cashloan.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.ajaya.cashloan.cl.domain.BorrowRepay;
import com.ajaya.cashloan.cl.model.DiversionModel;
import com.ajaya.cashloan.cl.model.MessageTypeModel;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.cl.service.UrgeRepayOrderService;
import com.ajaya.cashloan.core.common.util.DateUtils;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.model.BorrowModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.BeanUtil;

import javax.annotation.Resource;

/**
 * app公用接口
 *
 * @author jdd
 * @version 1.0
 * @date 2017年7月31日
 */
@Controller
@Scope("prototype")
public class AppCommonController extends BaseController {

    @Resource
    private ClBorrowService clBorrowServiceImpl;

    /**
     * 页面用的参数
     */
    @RequestMapping(value = "/api/common/credit/param.htm")
    public void attribute(@RequestParam("userId") Long userId) {
        String indianCommonSwitch = Global.getValue("indian_common_switch");
        if ("true".equals(indianCommonSwitch)) {
            Borrow lastBorrow = clBorrowServiceImpl.findLastBorrow(userId);
            if (lastBorrow != null && (BorrowModel.STATE_AUTO_REFUSED.equals(lastBorrow.getState()) || BorrowModel.STATE_REFUSED.equals(lastBorrow.getState()))) {
                String commomParam = Global.getValue("indan_commom_param");
                String title = Global.getValue("indan_commom_title");
                String url = Global.getValue("indan_commom_url");
                HashMap<String, String> hashMap = new HashMap<>(8);
                hashMap.put("title", title);
                hashMap.put("value", commomParam);
                hashMap.put("url", url);
                if ("".equals(commomParam) && "".equals(title) && "".equals(url)) {
                    ServletUtils.writeToResponse(response, ResponseWrapper.success());
                    return;
                }
                ServletUtils.writeToResponse(response, ResponseWrapper.success(hashMap));
                return;
            }
        }
        HashMap<String, Object> hashMap = new HashMap<>(8);
        hashMap.put("code", 500);
        hashMap.put("msg", "");
        ServletUtils.writeToResponse(response, hashMap);
    }


    /**
     *页面导流开关控制接口
     *
     */
    @RequestMapping(value = "/api/common/diversion/switch.htm")
    public void diversion() {
        DiversionModel reject = new DiversionModel();
        reject.setOpenSwitch(Global.getValue("reject_diversion_switch"));
        reject.setTitle(Global.getValue("reject_diversion_title"));
        reject.setValue(Global.getValue("reject_diversion_value"));
        DiversionModel repay = new DiversionModel();
        repay.setOpenSwitch(Global.getValue("repay_diversion_switch"));
        repay.setTitle(Global.getValue("repay_diversion_title"));
        repay.setValue(Global.getValue("repay_diversion_value"));
        HashMap<String, Object> param = new HashMap<>(8);
        param.put("rejectDiversion",reject);
        param.put("repayDiversion",repay);
        ServletUtils.writeToResponse(response, ResponseWrapper.success(param));
    }
    /**
     * 获取帮助中心文案
     */
    @RequestMapping(value = "/api/common/help/getText.htm")
    public void getText() {
        String centerText = Global.getValue("help_center_text");
        JSONObject parse = JSONObject.parseObject(centerText);
        ServletUtils.writeToResponse(response, ResponseWrapper.success(parse));
    }



//    @RequestMapping("/urge/pushOrderByBorrowId.htm")
//    public void pushOrder(Long borrowId) throws Exception {
//        ClSmsService clSmsService = (ClSmsService) BeanUtil.getBean("clSmsService");
//        String messageType = MessageTypeModel.MESSAGE_TYPE_SMS_GET_MONEY;
//
//    clSmsService.sendGetMoneySms("9060005868",messageType);
////        clSmsService.sendGetMoneySms("8433561187",messageTypeQ);
//
//    }

    @Resource
    com.ajaya.cashloan.cl.service.UrgeOrderPushLogService UrgeOrderPushLogService;
    @Resource
    UrgeRepayOrderService urgeRepayOrderService;
    @RequestMapping(value = "/urge/pushOrder.htm" ,method = RequestMethod.GET)
    public void pushOrder(@RequestParam("borrowId") long borrowId) throws Exception {
//        String pathname = ListUtil.class.getResource("/config/file/" + "borrowIds" + ".txt").getFile();
//        File filename = new File(pathname);
//        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
//        BufferedReader br = new BufferedReader(reader);
//        String line = "";
//        while ((line = br.readLine()) != null) {
//            long borrowId = Long.parseLong(line);
            urgeRepayOrderService.saveOrder(borrowId);
            UrgeOrderPushLogService.pushUrgeOrder(borrowId);
//        }
    }


    @RequestMapping("/urge/pushRepaymentByBorrowId.htm")
    public void pushRepayment(Long borrowId) throws Exception {
        UrgeOrderPushLogService.pushRepaymet(borrowId);
    }
}