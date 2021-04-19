package com.ajaya.creditrank.cr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.creditrank.cr.service.CreditRatingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;

/**
 * 评分操作
 * @author ctt
 * @version 1.0.0
 * @date 2017-01-05 16:22:54

 */
@Controller
public class CreditRatingController extends BaseController {

	@Resource
	private CreditRatingService creditRatingService;
	
	@RequestMapping(value="/modules/manage/cr/result/testCreditRating.htm",method=RequestMethod.POST)
	public void testCreditRating() throws Exception {
		creditRatingService.saveCreditRating("1",7l);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}

}
