package com.ajaya.cashloan.manage.controller;


import com.ajaya.cashloan.cl.domain.SmsTpl;
import com.ajaya.cashloan.cl.mapper.SmsTplMapper;
import com.ajaya.cashloan.cl.service.ClSmsService;
import com.ajaya.cashloan.core.common.util.ServletUtils;
import com.ajaya.cashloan.core.common.util.sms.TalkingSmsUtils;
import com.ajaya.cashloan.core.common.web.controller.BaseController;
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

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明： 表格数据导入
 *
 * @author yanzhiqiang
 * @since 2020-03-09 11:02
 **/
@Controller
@Scope("prototype")
public class ExcelImport extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ExcelImport.class);
    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";

    @Resource

    private SmsTplMapper smsTplMapper;

    @Resource

    private ClSmsService  clSmsService;
    /**
     * 读入excel文件，解析后返回
     *
     * @param file 文件名称
     * @throws IOException 异常
     */
    @RequestMapping("/sms/batch/import.htm")
    public void readExcel(MultipartFile file, @RequestParam("smsType") String  smsType) throws IOException {
        //发送条数
        int sendCount = 0;
//        String smsTemplate = ret(smsType);

        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    String phone = getCellValue(row.getCell(0)).trim();
                    clSmsService.sendSms(phone,smsType);
                    logger.info("---文件名称" + file.getOriginalFilename() + "----------------批量发送推广短信第{}条！-----------------", rowNum);
                    sendCount++;
                }
            }
        }
        ServletUtils.writeToResponse(response, "---文件名称" + file.getOriginalFilename() + "发送条数:" + sendCount );
    }



    private static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if (null == file) {
            logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            logger.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }

    private static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(XLS)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    private static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            //数字
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            //字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            //Boolean
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            //公式
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            //空值
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            //故障
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
    public String ret(String type) {
        Map<String, Object> search = new HashMap<>();
        search.put("type", type);
        SmsTpl tpl = smsTplMapper.findSelective(search);
        return tpl.getTpl();
    }

}
