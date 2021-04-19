package com.ajaya.cashloan.rc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.cashloan.rc.domain.TppBusiness;
import com.ajaya.cashloan.rc.service.Simplezx91CountService;
import com.ajaya.cashloan.rc.service.TppBusinessService;
import com.ajaya.cashloan.rc.service.Zx91DetailService;

 /**
 * 风控数据统计-（简）通话记录统计Controller
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-09-08 15:53:59

 */
@Controller
public class Simplezx91CountController extends BaseController {

	@Resource
	private Simplezx91CountService simplezx91CountService;
	@Resource
	private Zx91DetailService Zx91DetailService;
	@Resource
	private TppBusinessService tppBusinessService;

	
	@RequestMapping("/modules/manage/rc/simplezxquery/testQuery.htm")
	public void page(@RequestParam(value = "idNo") String idNo,
			@RequestParam(value = "realName") String realName) {
		TppBusiness business = tppBusinessService.findByNid("Zx91Query",4l);
		Zx91DetailService.query91zx1003(idNo, realName, 1l, business);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
}
