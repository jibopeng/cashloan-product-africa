package com.ajaya.cashloan.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.domain.PayChannelManage;
import com.ajaya.cashloan.cl.model.ResponseWrapper;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.PayChannelManageService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.context.Global;
import com.ajaya.cashloan.core.common.exception.BussinessException;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.system.domain.SysConfig;
import com.ajaya.cashloan.system.service.SysConfigService;

import tool.util.NumberUtil;


/**
 * 协议Controller
 *
 * @author yanzhiqiang
 * @version 1.0.0
 * @date 2020-05-05 13:57:14
 */
@Scope("prototype")
@Controller
public class ProtocolController extends BaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(ProtocolController.class);

    @Resource
    private SysConfigService sysConfigService;

    

    /**
     * 获取协议清单
     */
    @RequestMapping(value = "/api/protocol/list.htm", method = RequestMethod.GET)
    public void list(  @RequestParam(value = "appFlag", required = false) String appFlag) {

        String title2 = Global.getValue("title2");

        Map<String, Object> data = new HashMap<>(8);
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<SysConfig> list = sysConfigService.listByCode("protocol_");
        for (SysConfig aList : list) {
            Map<String, Object> pro = new HashMap<>(8);
            pro.put("code", aList.getCode());
            if (title2.equals(appFlag)){
                pro.put("value","/h5"+ aList.getValue());
            } else {
                pro.put("value",aList.getValue());
            }
            pro.put("name", aList.getName());
            dataList.add(pro);
        }
        data.put("list", dataList);
        ServletUtils.writeToResponse(response, ResponseWrapper.success("The query was successful.", data));
    }

}
