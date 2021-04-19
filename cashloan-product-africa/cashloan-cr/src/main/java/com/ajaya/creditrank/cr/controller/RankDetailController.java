package com.ajaya.creditrank.cr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.Page;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.JsonUtil;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
import com.ajaya.creditrank.cr.domain.RankDetail;
import com.ajaya.creditrank.cr.service.RankDetailService;


 /**
 * 评分卡等级详情表Controller
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-06 11:27:25

 */
@Scope("prototype")
@Controller
public class RankDetailController extends BaseController {

	@Resource
	private RankDetailService rankDetailService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modules/manage/cr/rankDetail/page.htm", method = RequestMethod.POST)
	public void page(
			@RequestParam(value="search",required=false) String search,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		Map<String,Object> searchMap = JsonUtil.parse(search, Map.class);
		Page<RankDetail> page = rankDetailService.page(searchMap,current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
}
