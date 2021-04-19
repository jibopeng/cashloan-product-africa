package com.ajaya.cashloan.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajaya.cashloan.cl.domain.SysUserOperationRecord;
import com.ajaya.cashloan.cl.domain.UserContacts;
import com.ajaya.cashloan.cl.domain.UserMessages;
import com.ajaya.cashloan.cl.domain.UserRemark;
import com.ajaya.cashloan.cl.mapper.SysUserOperationRecordMapper;
import com.ajaya.cashloan.cl.service.ClBorrowService;
import com.ajaya.cashloan.cl.service.UserContactsService;
import com.ajaya.cashloan.cl.service.UserMessagesService;
import com.ajaya.cashloan.cl.service.UserRemarkService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.domain.Borrow;
import com.ajaya.cashloan.core.service.CloanUserService;
import com.ajaya.cashloan.system.domain.SysUser;
import com.github.pagehelper.Page;

/**
* 用户信息Controller
* 
* @author lyang
* @version 1.0.0
* @date 2017-02-28 10:24:45
*/
@Scope("prototype")
@Controller
public class ManageMsgController extends ManageBaseController{

	@Resource
	private UserContactsService userContactsService;
	@Resource
	private UserMessagesService userMessagesService;
	@Resource
	private CloanUserService cloanUserService;
	@Resource 
	private SysUserOperationRecordMapper sysUserOperationRecordMapper;
	
	public static final Logger logger = LoggerFactory.getLogger(ManageMsgController.class);

	/**
	 * 通讯录查询
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/msg/listContacts.htm")
	public void listContacts(
			@RequestParam(value="userId") long userId,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		System.out.println("信审通讯录");
		Page<UserContacts> page = userContactsService.listContacts(userId,current,pageSize);
		Map<String, Object> data = new HashMap<>();
		data.put("list", page.getResult());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	
	@RequestMapping(value = "/modules/manage/msg/listContactsXinShen.htm")
	public void listContactsXinShen(
			@RequestParam(value="userId") long userId,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		System.out.println("信审通讯录");
		Page<UserContacts> page = userContactsService.listContacts(userId,current,pageSize);
		Map<String, Object> data = new HashMap<>();
		data.put("list", page.getResult());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	@RequestMapping(value = "/modules/manage/msg/listContactsXinShenAll.htm")
	public void listContactsXinShenAll(
			@RequestParam(value="userId") long userId,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		System.out.println("信审通讯录所有");
		List<UserContacts> list = userContactsService.listContacts(userId);
		Map<String, Object> data = new HashMap<>();
		data.put("list", list);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 通讯录查询
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@Resource
	private UserRemarkService userRemarkService;
	@Resource
	private ClBorrowService clBorrowService;
	@RequestMapping(value = "/modules/manage/msg/searchLike.htm")
	public void searchLike(
			@RequestParam(value="userId") long userId,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "phone",required=false) String phone) throws Exception {
		System.out.println("通讯录模糊查询");
		Page<UserContacts> page = userContactsService.listContactsLike(userId,current,pageSize,name,phone);
		Map<String, Object> data = new HashMap<>();
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		if(StringUtil.isNotBlank(name) || StringUtil.isNotBlank(phone)){
			UserRemark userRemark = new UserRemark();
			userRemark.setUserId(userId);
			userRemark.setSysUserId(sysUser.getId());
			//获取用户最近的一笔订单
			Borrow borrow = clBorrowService.findLastBorrow(userId);
			if(borrow != null){
				userRemark.setBorrowId(borrow.getId());
			}
			userRemark.setName(name);
			userRemark.setPhone(phone);
			userRemark.setType("1");
			userRemark.setCreateTime(new Date());
			int insert = userRemarkService.insert(userRemark);
			logger.info("sysUserId:" + sysUser.getId() + ",保存userId," + userId + ",结果:" + insert);
		}
		data.put("list", page.getResult());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 刘晓亮 给用户的通讯录添加备注信息
	 * @param userId
	 * @param contactPhone
	 * @param contactName
	 * @param contactRemark
	 */
	@RequestMapping(value = "/modules/manage/msg/saveContactRemark.htm")
	public void saveContactRemark(
			@RequestParam(value="userId") long userId,
			@RequestParam(value="contactPhone") String contactPhone,
			@RequestParam(value="contactName") String contactName,
			@RequestParam(value="contactRemark") String contactRemark){
		userContactsService.addOrUpdateContactRemark(userId,contactPhone,contactName,contactRemark);
		//保存催收员加的备注信息
		
		Subject user = SecurityUtils.getSubject();
		SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
		
		SysUserOperationRecord userOperationRecord = new SysUserOperationRecord();
		userOperationRecord.setSysUserId(sysUser.getId());
		userOperationRecord.setSysUserName(sysUser.getName());
		userOperationRecord.setSysUserOperationSource("催收员添加备注功能");
		userOperationRecord.setSysUserPrepare1(contactPhone);
		userOperationRecord.setSysUserPrepare2(contactName);
		userOperationRecord.setBorrowId(userId);
		userOperationRecord.setSysUserResult(contactRemark);
		userOperationRecord.setCreateTime(new Date());
		sysUserOperationRecordMapper.save(userOperationRecord);
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "新增成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	
	/**
	 * 短信查询
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/msg/listMessages.htm")
	public void listMessages(
			@RequestParam(value="userId") long userId,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		Page<UserMessages> page = userMessagesService.listMessagesXinShen(userId,current,pageSize);
		Map<String, Object> data = new HashMap<>();
		data.put("list", page.getResult());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
}
