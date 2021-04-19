package com.ajaya.cashloan.rc.controller;

import javax.annotation.Resource;

import com.ajaya.cashloan.rc.service.Zx91DetailService;
import org.springframework.stereotype.Controller;

import com.ajaya.cashloan.core.common.web.controller.BaseController;

/**
 * 风控数据统计-（简）通话记录统计Controller
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:52:59

 */
@Controller
public class Zx91DetailController extends BaseController {

	@Resource
	private Zx91DetailService zx91DetailService;

}
