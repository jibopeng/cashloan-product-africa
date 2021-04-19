package com.ajaya.cashloan.manage.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.ajaya.cashloan.cl.domain.UserBlackInfo;
import com.ajaya.cashloan.cl.model.RepayExcelModel;
import com.ajaya.cashloan.cl.service.UserBlackInfoService;
import com.ajaya.cashloan.core.common.context.Constant;
import com.ajaya.cashloan.core.common.util.RdPage;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.StringUtil;
import com.ajaya.cashloan.core.common.web.controller.BaseController;

/**
 * 黑名单,白名单Controller
 * 
 * @author caitt
 * @version 1.0.0
 * @date 2017-07-12 15:23:07
 */
@Controller
@Scope("prototype")
public class UserBlackInfoController extends BaseController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserBlackInfoController.class);

	@Resource
	private UserBlackInfoService userBlackInfoService;
	
	
	
	/**
	 * 导入黑名单信息
	 * @param file
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/userBlack/importUserBlack.htm", method = RequestMethod.POST)
	public void importUserInfo(@RequestParam(value = "userInfoFile") MultipartFile userInfoFile,@RequestParam(value = "type") String type) throws Exception{
		Map<String,Object> result = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(type) && ("10".equals(type) || "20".equals(type)) &&
				userInfoFile!=null && (userInfoFile.getOriginalFilename().endsWith(".xls") || userInfoFile.getOriginalFilename().endsWith(".xlsx"))){
			List resultList = userBlackInfoService.importUserInfo(userInfoFile,type);
	    	String title = "导入结果";
	    	RepayExcelModel report = new RepayExcelModel();
			String fileName = report.saveExcelByList1(resultList, title, userInfoFile.getOriginalFilename(),request);
	    	result.put(Constant.RESPONSE_DATA, "/modules/manage/userBlack/downResult.htm?path="+fileName);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "导入处理完成，点击确定下载导入结果。");
			ServletUtils.writeToResponse(response,result);
		}else{
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "参数非法");
			ServletUtils.writeToResponse(response,result);
		}
	}
	
	/**
	 * 批量还款成功后下载结果文件
	 * @param path
	 */
	@RequestMapping(value = "/modules/manage/userBlack/downResult.htm")
	public void downRepayByBatch(@RequestParam(value = "path") String path) {
		RepayExcelModel report = new RepayExcelModel();
		try {
			report.exportFile(path, request, response);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 查询黑名单列表
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/userBlack/listInfo.htm", method = RequestMethod.POST)
	public void listInfo(@RequestParam(value="searchParams",required=false) String search,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize,
			HttpServletResponse response) throws Exception {
		Map<String,Object> searchMap = JSONObject.parseObject(search);
		Page<UserBlackInfo> page = userBlackInfoService.listInfo(searchMap,current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page.getResult());
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 删除名单
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/userBlack/deleteBlack.htm", method = RequestMethod.POST)
	public void deleteBlack(@RequestParam(value = "id") Long id) throws Exception {
		int rtValue = userBlackInfoService.deleteByID(id);
		Map<String,Object> result = new HashMap<String,Object>();
		if(rtValue>0){
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "删除成功");
		}else{
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "删除失败");
		}
		ServletUtils.writeToResponse(response,result);
	}
	
	
	@RequestMapping(value = "/modules/manage/userBlack/downTemplete.htm", method = RequestMethod.GET)
	public void downTemplete(HttpServletRequest request,HttpServletResponse response) throws IOException {

//		//获得请求文件名  
        String filename = "黑名单模板.xlsx";  
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
        ServletContext servletContext = webApplicationContext.getServletContext();  
        String sFileName = filename;
		String userAgent = request.getHeader("User-Agent");
//		// 判断是否火狐浏览器
		boolean isFirefox = (userAgent != null && userAgent.contains("Firefox"));
		if (isFirefox) {
			sFileName = new String(sFileName.getBytes("UTF-8"), "ISO-8859-1");
		} else {
			sFileName = URLEncoder.encode(sFileName, "UTF8");
		}

        response.setHeader("Content-Disposition", "attachment;filename="+sFileName);  
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        String fullFileName = servletContext.getRealPath("/template/" + filename);  
        response.getOutputStream().write(File2byte(fullFileName));
	}
	
	public static byte[] File2byte(String filePath) {
		byte[] buffer = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			logger.error("黑名单导入模板下载出错");
		} catch (IOException e) {
			logger.error("黑名单导入模板下载出错");
		} finally {
			try {
				fis.close();
				bos.close();
				bos.flush();
			} catch (IOException e) {
				logger.error("黑名单导入模板下载出错");
			}
			
		}
		return buffer;
	}


}
