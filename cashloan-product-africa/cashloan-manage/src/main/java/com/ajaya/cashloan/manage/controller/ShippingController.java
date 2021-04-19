/*package com..cashloan.manage.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com..cashloan.cl.domain.OrderFeng;
import com..cashloan.cl.domain.Shipping;
import ClBorrowService;
import com..cashloan.cl.service.OrderFengService;
import com..cashloan.cl.service.ShippingService;
import ServletUtils;
import Borrow;

*//**
 * 
 * 物流信息controller
 *
 *//*
@Scope("prototype")
@Controller
public class ShippingController extends ManageBaseController {
	
	private final static Logger logger = LoggerFactory.getLogger(ShippingController.class);
	
	@Resource
	private OrderFengService orderFengService;
	@Resource
	private ShippingService shippingService;
	@Resource
	private ClBorrowService clBorrowService;

	@RequestMapping(value = "/modules/manage/shipping/importInfo.htm")
	public void importLogisticsInfo(@RequestParam(value = "file") MultipartFile file){
		Map<String ,Object> map = new HashMap<String,Object>();
		int i = 0;
		int y = 0;
		String message = "";
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
				Sheet sheet = wb.getSheetAt(0);
				int rowNum = sheet.getLastRowNum();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				for(;i <= rowNum;i++){
					Shipping shipping = null;
					Row row = sheet.getRow(i);
					//borrowId
					Cell cell0 = row.getCell(0);
					//快递公司
					Cell cell1 = row.getCell(1);
					//快递单号
					Cell cell2 = row.getCell(2);
					String borrowId = cell0.getStringCellValue();
					String company = cell1.getStringCellValue();
					Borrow borrow = clBorrowService.getById(Long.parseLong(borrowId));
					if(borrow == null){
						logger.info("——第" + (i+1) + "borrowId不存在——");
						message += "——第" + (i+1) + "borrowId不存在——";
						continue;
					}
					OrderFeng orderFeng = orderFengService.findByBorrowId(borrow);
					if(orderFeng != null){
						shipping = shippingService.findByOrderId(orderFeng.getId());
						if(shipping == null){
							shipping = new Shipping();
						}
						shipping.setOrderId(orderFeng.getId());
						shipping.setCreateTime(new Date());
						shipping.setModifyTime(new Date());
						if(company.indexOf("百世") > -1){
							shipping.setDeliveryCorp("百世快递");
						}else{
							shipping.setDeliveryCorp(company);
						}
						shipping.setMemo(sdf.format(new Date()));
						shipping.setFreight(0f);
						shipping.setName(orderFeng.getName());
						shipping.setOperator("操作员");
						shipping.setPhone(orderFeng.getPhone());
						shipping.setTrackingNo(cell2.getStringCellValue());
						shipping.setAddress(orderFeng.getAddress());
						if(shipping.getId() != null){
							int k = shippingService.updateById(shipping);
						}else{
							int k = shippingService.insert(shipping);
						}
						orderFeng.setStatus(100);
						orderFengService.updateById(orderFeng);
					}
					y++;
					System.out.println("已经完成第=============="+(i+1)+"=============条");
				}
			}
			map.put("code", 200);
			map.put("msg", "导入数据" + i + "条,成功" + y + "条,失败" + (i-y) + "条件," + message);
		} catch (Exception e) {
			logger.error("导入物流信息数据异常"+e.getMessage());
			map.put("code", 400);
			map.put("msg", "导入数据异常");
			e.printStackTrace();
		}finally {
			ServletUtils.writeToResponse(response, map);
		}
	}
}
*/