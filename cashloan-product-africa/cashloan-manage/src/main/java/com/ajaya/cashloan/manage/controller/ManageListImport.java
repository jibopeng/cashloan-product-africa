package com.ajaya.cashloan.manage.controller;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ajaya.cashloan.cl.domain.SysUserOperationRecord;
import com.ajaya.cashloan.cl.domain.UrgeRepayOrder;
import com.ajaya.cashloan.cl.model.UrgeRepayOrderModel;
import com.ajaya.cashloan.cl.service.SysUserOperationRecordService;
import com.ajaya.cashloan.cl.service.UrgeRepayOrderService;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.system.domain.SysUser;
import com.ajaya.cashloan.system.mapper.SysUserMapper;
import com.ajaya.cashloan.system.service.SysUserService;

/**
 * 
 * 导入excel类
 *
 */
@Controller
@Scope(value="prototype")
public class ManageListImport extends ManageBaseController {

	private Logger logger = LoggerFactory.getLogger(ManageListImport.class);
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private UrgeRepayOrderService urgeRepayOrderService;
	@Resource 
	private SysUserOperationRecordService sysUserOperationRecordService;
	
	@RequestMapping(value = "/modules/manage/division/import.htm")
	public void importLogisticsInfo(@RequestParam(value = "file") MultipartFile file){
		Map<String ,Object> map = new HashMap<String,Object>();
		int n = 0;
		int y = 0;
		int msg = 0;
		int blankRow = 0;
		try {
			byte[] b = file.getBytes();
			if(b != null){
				String fileName = file.getOriginalFilename();
				//建立输入流
				InputStream input = file.getInputStream();
				Workbook wb = null;
				if(fileName.endsWith(".xls")){
					wb = new HSSFWorkbook(input);  
				}else{
					wb = new XSSFWorkbook(input);
				}
				//获取sheet页数量，得到需要分案人员的数量
				int numberOfSheets = wb.getNumberOfSheets();
				if(numberOfSheets <= 0){
					logger.info("月底重新分案，无分案人员");
					map.put("code", 200);
					map.put("msg", "无分案人员");
					ServletUtils.writeToResponse(response, map);
					return;
				}
				for(int k = 0;k < numberOfSheets;k++){
					//获取sheet内容
					Sheet sheet = wb.getSheetAt(k);
					//获取每个sheet页面的行数
					int rowNum = sheet.getLastRowNum();
					//获取所有分案条数
					n = n + rowNum + 1;
					//获取sheet页名称（催收人员的名称）
					String name = sheet.getSheetName();

					logger.info("sheetname=" + name + ",rownum=" + rowNum);
					
					for(int i = 0 ; i <= rowNum;i++){
						Row row = sheet.getRow(i);
						logger.info("sheetname=" + name + ",第" + (i+1) + "行数据");
						//orderNo
						if(row == null){
							logger.info("sheetname=" + name + ",第" + (i+1) + "行数据为空行");
							++blankRow;
							continue;
						}
						Cell cell0 = row.getCell(0);
						//原催收人员姓名
						Cell cell1 = row.getCell(11);
						//现在催收人员姓名
						Cell cell2 = row.getCell(12);
						if(cell0 == null || cell1 == null || cell2 == null){
							logger.info("月底重新分案，userName=" + name + "第" + (i+1) + "条数据orderNo或originalName或者name为null");
							continue;
						}
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						String orderNo = cell0.getStringCellValue().trim();
						String originalName = cell1.getStringCellValue().trim();
						name = cell2.getStringCellValue().trim();
						
						//查询催收人员名字是否存在
						Map<String, Object> mapSysUser = new HashMap<>();
						mapSysUser.put("name", name);
						SysUser sysUserName = sysUserMapper.findSelective(mapSysUser);
						
						if(sysUserName == null){
							mapSysUser.clear();
							mapSysUser.put("userName", name);
							sysUserName = sysUserMapper.findSelective(mapSysUser);
						}
						
						//SysUser sysUserName = sysUserService.getUserByUserName(name);
						if(sysUserName == null){
							logger.info("月底重新分案，userName=" + name + "的分案人员不存在");
//							map.put("code", 200);
//							map.put("msg", "分案人员不存在");
//							ServletUtils.writeToResponse(response, map);
//							return;
							continue;
						}
						
						Map<String,Object> orderMap = new HashMap<String,Object>();
						orderMap.put("orderNo", orderNo);
						UrgeRepayOrder urgeRepayOrder = urgeRepayOrderService.findOrderByMap(orderMap);
						if(urgeRepayOrder == null){
							logger.info("月底重新分案，orderNo=" + orderNo + "的订单不存在");
							map.put("code", 200);
							map.put("msg", "订单不存在");
							ServletUtils.writeToResponse(response, map);
							return;
						}
						
						logger.info("订单状态state=" + urgeRepayOrder.getState());
						logger.info(JSON.toJSONString(urgeRepayOrder));
						//已经催收成功，那么不进行任何处理
						if("40".equals(urgeRepayOrder.getState())){
							logger.info("月底重新分案，orderNo=" + orderNo + "的订单已经催收成功");
							continue;
						}
						//获取当前操作人
						Subject user = SecurityUtils.getSubject();
						SysUser sysUser =  (SysUser) user.getSession().getAttribute("SysUser");
						
						//保存月底重新分案记录
						SysUserOperationRecord sysUserOperationRecord = new SysUserOperationRecord();
						sysUserOperationRecord.setSysUserId(sysUser.getId());
						sysUserOperationRecord.setSysUserName(sysUser.getName());
						sysUserOperationRecord.setBorrowId(urgeRepayOrder.getBorrowId());
						sysUserOperationRecord.setSysUserResult("月底重新分配催收人员");
						sysUserOperationRecord.setSysUserOperationSource("后台月底重新分配催收功能");
						sysUserOperationRecord.setSysUserPrepare1(urgeRepayOrder.getBorrowName());
						//订单原分配的催收人员
						sysUserOperationRecord.setSysUserPrepare2(originalName);
						//订单现分配的催收人员
						sysUserOperationRecord.setSysUserRemark(name);
						sysUserOperationRecord.setCreateTime(new Date());
						int insert = sysUserOperationRecordService.insert(sysUserOperationRecord);
						logger.info("月底重新分案,orderNo=" + orderNo + "向系统人员操作记录表中插入数据insert=" + insert);
						
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("id", urgeRepayOrder.getId());
						params.put("userId", sysUserName.getId());
						params.put("userName", sysUserName.getUserName());
						params.put("state", UrgeRepayOrderModel.STATE_ORDER_WAIT);
						params.put("updateTime", new Date());
						msg = urgeRepayOrderService.orderAllotUser(params);
						logger.info("月底重新分案,orderNo=" + orderNo + "重新分配催收人员msg=" + msg);
						y++;
					}
				}
			}
			map.put("code", 200);
			map.put("msg", "导入数据" + n + "条,成功" + y + "条,失败" + (n-y) + "条,空行数据blank=" + blankRow + "条");
		} catch (Exception e) {
			logger.error("月底重新分案导入数据异常"+e.getMessage());
			map.put("code", 400);
			map.put("msg", "导入数据异常");
			e.printStackTrace();
		}finally {
			ServletUtils.writeToResponse(response, map);
		}
	}
}
