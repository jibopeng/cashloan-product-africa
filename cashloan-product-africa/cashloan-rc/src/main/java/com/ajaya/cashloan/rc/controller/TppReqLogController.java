package com.ajaya.cashloan.rc.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.rc.service.TppReqLogService;

/**
 * 第三方征信请求记录Controller
 * 
 * @author zlh
 * @version 1.0.0
 * @date 2017-03-20 13:50:34

 */
@Controller
public class TppReqLogController extends BaseController {

	@Resource
	private TppReqLogService tppReqLogService;

}
