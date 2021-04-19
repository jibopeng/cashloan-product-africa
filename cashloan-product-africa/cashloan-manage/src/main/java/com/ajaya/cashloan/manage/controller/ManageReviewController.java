package com.ajaya.cashloan.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.rule.domain.BorrowRuleResult;
import com.ajaya.cashloan.rule.model.ManageRuleResultModel;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.domain.SysUserRole;
import com.ajaya.cashloan.system.service.SysUserRoleService;

/**
* 代理用户信息Controller
* 
* @author lyang
* @version 1.0.0
* @date 2017-02-28 10:24:45
*/
@Scope("prototype")
@Controller
public class ManageReviewController extends ManageBaseController{

	@Resource
	private ClBorrowService clBorrowService;
	
	@Resource
	private SysUserRoleService sysUserRoleService;
	
	private static final Logger logger = Logger.getLogger(ManageReviewController.class);
	/**
	 * 人工复审信息查询
	 * @param borrowId
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/review/findResult.htm")
	public void findResult(
			@RequestParam(value="borrowId") long borrowId
			) throws Exception{
		Map<String,Object> data =  clBorrowService.findResult(borrowId);
		Map<String,Object> result = new HashMap<String,Object>();
		
		//刘晓亮 start
		//获取当前用户id
		//使用的shiro
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		//获取当前用户拥有的所有角色list
		List<SysUserRole> roleList = sysUserRoleService.getSysUserRoleList(sysUser.getId());
		//判断角色中是否有系统管理员
		Boolean flag = false;
		if(roleList != null && roleList.size() > 0){
			for (SysUserRole sysUserRole : roleList) {
				//系统管理员的role_id是3   此时要注意,这里判断的role_id,要保证 arc_sys_role 表中id=1的是风控管理员,所以风控管理员这条记录的id是不能变的,必须为3
				if(sysUserRole.getRoleId() == 3){
					//说明当前用户拥有系统管理员角色
					flag = true;
					break;
				}
			}
		}
		//flag为false说明用户没有系统管理员角色
		flag = true;
		if(!flag){
			//说明用户没有系统管理员角色
			List<List<ManageRuleResultModel>> resultList = (List<List<ManageRuleResultModel>>) data.get("resultList");
			if(resultList != null && resultList.size() > 0){
				for (List<ManageRuleResultModel> manageRuleResultModelListTemp : resultList) {
					if(manageRuleResultModelListTemp != null && manageRuleResultModelListTemp.size() > 0){
						for (ManageRuleResultModel manageRuleResultModelTemp : manageRuleResultModelListTemp) {
							if(manageRuleResultModelTemp != null){
								List<BorrowRuleResult> infoList = manageRuleResultModelTemp.getInfoList();
								List<BorrowRuleResult> infoListTemp = new ArrayList<BorrowRuleResult>();
								if(infoList != null && infoList.size() > 0){
									for(int i = 0; i < infoList.size() ; i++){
										BorrowRuleResult borrowRuleResult = infoList.get(i);
										Long ruleId = borrowRuleResult.getRuleId();
										logger.info("ruleId:" +ruleId);
										logger.info("TbName:" + borrowRuleResult.getTbName());
										logger.info("ColName:" + borrowRuleResult.getColName());
										infoList.get(i).setTbName("Rule" + ruleId);
										infoList.get(i).setColName("RuleEngine" + borrowRuleResult.getRuleEngineConfigId());
										//infoList.get(i).setTbName("######");
										//infoList.get(i).setColName("*****");
										//infoList.get(i).setRule("*****");
										//infoList.get(i).setValue("*****");
										infoListTemp.add(infoList.get(i));
									}
								}
								manageRuleResultModelTemp.setInfoList(infoListTemp);
							}
						}
					}
				}
			}
		}
		//刘晓亮 end
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
}
