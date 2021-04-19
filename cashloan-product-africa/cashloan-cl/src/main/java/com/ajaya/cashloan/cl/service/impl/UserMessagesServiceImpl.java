package com.ajaya.cashloan.cl.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ajaya.cashloan.cl.service.UserMessagesService;
import com.ajaya.cashloan.cl.model.UserMessagesModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ajaya.cashloan.cl.domain.UserMessages;
import com.ajaya.cashloan.cl.mapper.UserMessagesMapper;
import com.ajaya.cashloan.core.common.context.ExportConstant;
import com.ajaya.cashloan.core.common.mapper.BaseMapper;
import com.ajaya.cashloan.core.common.service.impl.BaseServiceImpl;
import com.ajaya.cashloan.core.common.util.ShardTableUtil;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.mapper.UserMapper;
import com.ajaya.cashloan.core.model.UserConnactModel;
import com.ajaya.cashloan.core.model.UserVoiceModel;


/**
 * 用户资料--联系人ServiceImpl
 *
 * @author chenxy
 * @version 1.0.0
 * @date 2017-03-04 11:54:57
 */

@Service("clUserMessagesService")
public class UserMessagesServiceImpl extends BaseServiceImpl<UserMessages, Long> implements UserMessagesService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserMessagesServiceImpl.class);

	@Resource
	private UserMessagesMapper clUserMessagesMapper;

	@Resource
	private UserMapper userMapper;

	@Override
	public BaseMapper<UserMessages, Long> getMapper() {
		return clUserMessagesMapper;
	}

	@Override
	public Page<UserMessages> listMessages(long userId, int current, int pageSize) {
		PageHelper.startPage(current, pageSize);
		Map<String,Object> searchMap = new HashMap<>();
		searchMap.put("userId", userId);
		List<UserMessages> list = clUserMessagesMapper.listSelective(searchMap);
		for (UserMessages clUserMessages : list) {
			if ("10".equals(clUserMessages.getType())) {
				clUserMessages.setType("发送");
			}else {
				clUserMessages.setType("接收");
			}
		}
		return (Page<UserMessages>)list;
	}
	@Override
	public HSSFWorkbook findUserMesByuPhones(String[] phones) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//第一部分   查询客户信息
		ArrayList<Map<String, Object>> list = new ArrayList<>();
		ArrayList<String> ides = new ArrayList<>();
		for (String phone : phones) {
			HashMap<String, Object> userBaseMes = userMapper.findUserMesByuPhone(phone);
			//如果没查到信息则跳出循环
			if (userBaseMes==null) {
				continue;
			}
			list.add(userBaseMes);
			ides.add(userBaseMes.get("user_id")+"");
		}
		//1.创建一个excel对象
		HSSFWorkbook excel = new HSSFWorkbook();
		//2.样式
		//单元格样式
		HSSFCellStyle cellStyle = excel.createCellStyle();
		HSSFCellStyle fontStyle = excel.createCellStyle();
		//创建一个居中样式
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//字体样式
		HSSFFont font = excel.createFont();
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontStyle.setFont(font);
		//创建一个居中样式
		fontStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//2.在excel中添加一个sheet
		HSSFSheet sheet = excel.createSheet("基本信息");
		//3.在sheet中添加第0行:标题（x）
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("基本信息");
		cell.setCellStyle(fontStyle);
		//合并20列成一列，放大标题(起始行，截至行，起始列， 截至列)
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 19));
		//4.创建单元格，设置表头，以及表头样式
		//创建第二行，开始写表头
		row = sheet.createRow(1);
		//第二行
		String[] heads = ExportConstant.EXPORT_USERBASEMESSAGE_LIST_HEARDERS;
		for (int i = 0; i < heads.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(heads[i]);
			cell.setCellStyle(cellStyle);
		}
		//5.第三行写入数据
		String[] fileds = ExportConstant.EXPORT_USERBASEMESSAGE_LIST_FIELDS;
		for (int j = 0; j < list.size(); j++) {
			HSSFRow rows = sheet.createRow(j+2);
			for (int i = 0; i < fileds.length; i++) {
				cell = rows.createCell(i);
				HashMap<String, Object> map = (HashMap<String, Object>) list.get(j);
				String string = fileds[i].toString();
				String  object = map.get(string)+"";
				//时间格式处理
				if ("sysdate,create_time,repay_time".contains(string)) {
					try {
						Date date = dateFormat.parse(object);
						object = dateFormat.format(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				cell.setCellValue( object);
				cell.setCellStyle(cellStyle);
			}
		}
		//第二部分客户通讯录	
		//2.在excel中添加一个sheet
		HSSFSheet sheettwo = excel.createSheet("通讯录");
		//3.在sheet中添加第0行:标题（x）
		HSSFRow rowt = sheettwo.createRow(0);
		HSSFCell cellt = rowt.createCell(0);
		cellt.setCellValue("通讯录");
		cellt.setCellStyle(fontStyle);
		//合并4列成一列，放大标题(起始行，截至行，起始列， 截至列)
		sheettwo.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		//4.创建单元格，设置表头，以及表头样式
		//创建第二行，开始写表头
		rowt = sheettwo.createRow(1);
		String[] headt = ExportConstant.EXPORT_USERCONNACTLIST_HEARDERS;
		for (int i = 0; i < headt.length; i++) {
			cellt = rowt.createCell(i);
			cellt.setCellValue(headt[i]);
			cellt.setCellStyle(cellStyle);
		}
		//根据客户id进行分表查询
		int number = 0;
		for (String ide : ides) {
			long id = Long.parseLong(ide);
			//分表查询
			String tableName = ShardTableUtil.generateTableNameById("cl_user_contacts", id, 30000);
			List<UserConnactModel>   lists =userMapper.findUserConnactByid(tableName,id+"");
			//封装第二页数据信息
			for (int i = 0; i < lists.size(); i++) {
				HSSFRow rowts = sheettwo.createRow(number+2);
				HSSFCell cell0 = rowts.createCell(0);
				HSSFCell cell1 = rowts.createCell(1);
				HSSFCell cell2 = rowts.createCell(2);
				HSSFCell cell3 = rowts.createCell(3);
				cell0.setCellStyle(cellStyle);
				cell1.setCellStyle(cellStyle);
				cell2.setCellStyle(cellStyle);
				cell3.setCellStyle(cellStyle);
				UserConnactModel userConnactModel = lists.get(i);
				cell0.setCellValue(userConnactModel.getUserid());
				cell1.setCellValue(userConnactModel.getRealname());
				cell2.setCellValue(userConnactModel.getName());
				cell3.setCellValue(userConnactModel.getPhone());
				number++;
			}
		}
		//第三客户通话记录部分
		//2.在excel中添加一个sheet
		HSSFSheet sheetthree = excel.createSheet("通话记录");
		//3.在sheet中添加第0行:标题（x）
		HSSFRow rowth = sheetthree.createRow(0);
		HSSFCell cellth = rowth.createCell(0);
		cellth.setCellValue("通话记录");
		cellth.setCellStyle(fontStyle);
		sheetthree.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));//合并6列成一列，放大标题(起始行，截至行，起始列， 截至列)
		//4.创建单元格，设置表头，以及表头样式
		//创建第二行，开始写表头
		rowth = sheetthree.createRow(1);
		String[] headth =  ExportConstant.EXPORT_USERVOICESLIST_HEARDERS;
		for (int i = 0; i < headth.length; i++) {
			cellth = rowth.createCell(i);
			cellth.setCellValue(headth[i]);
			cellth.setCellStyle(cellStyle);
		}
		//根据客户id进行分表查询
		int numberthree = 0;
		for (String ide : ides) {
			//分表查询
			long id = Long.parseLong(ide);
			String tableName = ShardTableUtil.generateTableNameById("cl_user_voices", id, 30000);
			List<UserVoiceModel>   lists =userMapper.findUserVoicesByid(tableName,id+"");
			//封装第三页数据信息
			for (int i = 0; i < lists.size(); i++) {
				HSSFRow rowts = sheetthree.createRow(numberthree+2);
				HSSFCell cell0 = rowts.createCell(0);
				HSSFCell cell1 = rowts.createCell(1);
				HSSFCell cell2 = rowts.createCell(2);
				HSSFCell cell3 = rowts.createCell(3);
				HSSFCell cell4 = rowts.createCell(4);
				HSSFCell cell5 = rowts.createCell(5);
				HSSFCell cell6 = rowts.createCell(6);
				cell0.setCellStyle(cellStyle);
				cell1.setCellStyle(cellStyle);
				cell2.setCellStyle(cellStyle);
				cell3.setCellStyle(cellStyle);
				cell4.setCellStyle(cellStyle);
				cell5.setCellStyle(cellStyle);
				cell6.setCellStyle(cellStyle);
				UserVoiceModel userVoiceModel = lists.get(i);
				cell0.setCellValue(userVoiceModel.getUserid());
				cell1.setCellValue(userVoiceModel.getRealname());
				cell2.setCellValue(userVoiceModel.getPhonenum());
				//时间格式处理
				String voicedate = userVoiceModel.getVoicedate();
				try {
					Date date = dateFormat.parse(voicedate);
					voicedate = dateFormat.format(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				cell3.setCellValue(voicedate);
				cell4.setCellValue(userVoiceModel.getVoicetype());
				cell5.setCellValue(userVoiceModel.getVoiceduration());
				numberthree++;
			}
		}
		return excel;
	}
	

	@Override
	public ArrayList<Long> findUserIdByuPhone(String phone) {
		return  clUserMessagesMapper.findUserIdByuPhone(phone);
	}

	@Override
	public Map<String, Object> getMessageInfo(HashMap<String, Object> paramMap) {
		Long userId = Long.parseLong(paramMap.get("userId").toString());
		// 分表
		String tableName = ShardTableUtil.generateTableNameById("cl_user_messages", userId, 30000);
		int countTable = clUserMessagesMapper.countTable(tableName);
		if (countTable == 0) {
			clUserMessagesMapper.createTable(tableName);
		}
		Integer messageTimes = 0;
		Date minDate = null;
		Date maxDate = null;
		List<Long> dateList = new ArrayList<>();
		List<UserMessages> list = clUserMessagesMapper.listShardSelective(tableName, paramMap);
		if(list != null && list.size() > 0){
			messageTimes = list.size();
			for (UserMessages userMessages : list) {
				dateList.add(userMessages.getTime().getTime());
			}
		}
		
		if(dateList.size() > 0){
			minDate = new Date(Collections.min(dateList));
			maxDate = new Date(Collections.max(dateList));
		}
		
		Map<String, Object> result = new HashMap<>();
		result.put("messageTimes", messageTimes);
		result.put("minDate", minDate == null ? "" : minDate);
		result.put("maxDate", maxDate == null ? "" : maxDate);
		return result;
	}

	@Override
	public void updateMessagesBorrowidByUserid(Long userId, Long borrowId) {
		logger.info("更新用户ID： "+userId+", 订单ID： "+borrowId+" 的短信数据 ------");
		// 分表
		String tableName = ShardTableUtil.generateTableNameById("cl_user_messages", userId, 30000);
		int countTable = clUserMessagesMapper.countTable(tableName);
		if (countTable == 0) {
			clUserMessagesMapper.createTable(tableName);
		}
		// 用户没有订单号的短信数据
		List<UserMessages> userMessagesListNoBorrowid = clUserMessagesMapper.findListNoBorrowidByUserid(tableName, userId);
		if(userMessagesListNoBorrowid != null && userMessagesListNoBorrowid.size() > 0){
			List<Long> ids = new ArrayList<>();
			Date updateTime = new Date();
			for (int i = 0; i < userMessagesListNoBorrowid.size(); i++) {
				ids.add(userMessagesListNoBorrowid.get(i).getId());
//				UserMessages userMessages = userMessagesListNoBorrowid.get(i);
//				userMessages.setBorrowId(borrowId);
//				userMessages.setUpdateTime(updateTime);
//				clUserMessagesMapper.updateShard(tableName, userMessages);
			}
			clUserMessagesMapper.updateByIdsShard(tableName, borrowId, updateTime, ids);
		}
	}

	@Override
	public void deleteShard(String tableName, Long userId) {
		clUserMessagesMapper.deleteShardByUserId(tableName, userId);
	}

	@Override
	public int saveShard(String tableName, Map<String, Object> map, long userId, Date createTime) {
		String phone = StringUtil.isNull(map.get("phone")).replaceAll("-", "").replaceAll(" ", "");
		if(StringUtil.isNotBlank(phone) && phone.length() <= 20){
			try {
				UserMessages userMessages = new UserMessages();
				userMessages.setName(map.get("name")==null?"":map.get("name").toString());
				userMessages.setPhone(map.get("phone")==null?"":map.get("phone").toString());
				userMessages.setTime(new Date(Long.parseLong(map.get("time")+"")));
				userMessages.setType((map.get("type")==null?"":map.get("type").toString()));
				userMessages.setUserId(userId);
				userMessages.setMsg((map.get("msg")==null?"":map.get("msg").toString()));
				userMessages.setBorrowId(map.get("borrowId")==null ? null : Long.parseLong(map.get("borrowId").toString()));
				userMessages.setCreateTime(createTime);
				return clUserMessagesMapper.saveShard(tableName, userMessages);
			} catch (Exception e) {
				logger.error("保存用户userId："+userId+"短信记录失败， phone：" + phone + ",", e);
			}
		} else {
			logger.error("保存用户userId："+userId+"短信记录失败， phone：" + phone);
			return 0;
		}
		return 0;
	}

	@Override
	public String getTableName(long userId) {
		String tableName = ShardTableUtil.generateTableNameById("cl_user_messages", userId, 30000);
		int countTable = clUserMessagesMapper.countTable(tableName);
		if (countTable == 0) {
			clUserMessagesMapper.createTable(tableName);
		}
		return tableName;
	}

	@Override
	public List<UserMessagesModel> selectMessagesListByBorrowIdShard(long borrowId, Long userId) {

		// 分表
		String tableName = ShardTableUtil.generateTableNameById("cl_user_messages",userId, 30000);
		int countTable = clUserMessagesMapper.countTable(tableName);
		if (countTable == 0) {
			clUserMessagesMapper.createTable(tableName);
		}
		return clUserMessagesMapper.selectMessagesListByBorrowIdShard(borrowId,tableName);
	}

	@Override
	public Long selectDataMaxBorrowIdBefore(long borrowId, Long userId) {
		String tableName = ShardTableUtil.generateTableNameById("cl_user_messages",userId, 30000);
		int countTable = clUserMessagesMapper.countTable(tableName);
		if (countTable == 0) {
			clUserMessagesMapper.createTable(tableName);
		}
		logger.info("borrowId:" + borrowId + ",userId:" + userId + ",tableName:" + tableName);
		return clUserMessagesMapper.selectDataMaxBorrowIdBefore(userId,borrowId,tableName);
	}

	@Override
	public Page<UserMessages> listMessagesXinShen(long userId, int current, int pageSize) {
		List<UserMessages> list = null;
		String tableName = ShardTableUtil.generateTableNameById("cl_user_messages",userId, 30000);
		Long borrowId = clUserMessagesMapper.findMaxBorrowidByUserid(tableName, userId);
        if(borrowId == null || borrowId == 0){
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            PageHelper.startPage(current, pageSize);
            list = clUserMessagesMapper.listShardSelective(tableName, params);
        } else {
        	PageHelper.startPage(current, pageSize);
            list = clUserMessagesMapper.listShardByBorrowid(tableName, borrowId);
        }
        return (Page<UserMessages>)list;
	}
}