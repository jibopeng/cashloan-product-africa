package com.ajaya.cashloan.api.controller;

import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.UserVirtualAccountService;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.util.GetRequestJsonUtils;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.SHAUtil;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.alibaba.fastjson.JSON;
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
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能说明：线下还款相关业务
 *
 * @author yanzhiqiang
 * @since 2021-01-01 17:21
 */

@Controller
@Scope("prototype")
public class TeamAptController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(TeamAptController.class);

    @Resource
    private UserVirtualAccountService userVirtualAccountServiceImpl;

    /**
     * 根据用户id 生成虚拟账号
     *
     * @param userId 用户id
     */
    @RequestMapping(value = "/api/act/repay/getBankTransferInfo.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void getBankTransferInfo(@RequestParam(value = "userId") Long userId,@RequestParam(value = "appFlag", required = false) String appFlag
            ,@RequestParam(value = "borrowId") Long borrowId) {
        ResponseWrapper responseWrapper = userVirtualAccountServiceImpl.getBankTransferInfo(userId,appFlag,borrowId);
        ServletUtils.writeToResponse(response, responseWrapper);
    }

    /**
     * 根据用户id 生成ussd还款列表
     *
     * @param userId 用户id
     */
    @RequestMapping(value = "/api/act/repay/getUssdListInfo.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public void getUssdListInfo(@RequestParam(value = "userId") Long userId,@RequestParam(value = "appFlag", required = false) String appFlag
            ,@RequestParam(value = "borrowId") Long borrowId) {
        ResponseWrapper responseWrapper = userVirtualAccountServiceImpl.getUssdListInfo(userId,appFlag,borrowId);
        ServletUtils.writeToResponse(response, responseWrapper);
    }

    /**
     * 银行转账，ussd转账还款业务webhook处理
     *
     * @param payOutDetail 用户id
     */
    @RequestMapping(value = "/teamApt/callback/repay.htm", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public void callback(@RequestBody JSONObject payOutDetail) {
        try {
            boolean checkSign = checkSign(payOutDetail);
            if (!checkSign) {
                response.getWriter().write(200);
                return;
            }
            userVirtualAccountServiceImpl.dealTransferCallBack(payOutDetail);
            response.getWriter().write(200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkSign(JSONObject payOutDetail) {
        String contentStr = JSON.toJSONString(payOutDetail);
        String publicKey = Global.getValue("teamapt_secret_key");
        logger.info("contentStr: {}", contentStr);
        String reference = payOutDetail.getString("paymentReference");
        String amountPaid = payOutDetail.getString("amountPaid");
        String paidOn = payOutDetail.getString("paidOn");
        String transactionReference = payOutDetail.getString("transactionReference");
        String sign = payOutDetail.getString("transactionHash");
        // 验签需要字符串
        String allData = new StringBuilder().append(publicKey).append("|").append(reference).append("|")
                .append(amountPaid).append("|").append(paidOn).append("|").append(transactionReference).toString();

        String mySign = SHAUtil.SHA512(allData);
        boolean sig = mySign.equals(sign);
        logger.info("验签结果{}", sig);
        Map<String, Object> rec = new LinkedHashMap<>();
        if (!sig) {
            rec.put("code", 400);
            rec.put("msg", "验签失败");
            JsonUtil.writeJson(rec, response);
            return false;
        }
        return true;
    }
}
