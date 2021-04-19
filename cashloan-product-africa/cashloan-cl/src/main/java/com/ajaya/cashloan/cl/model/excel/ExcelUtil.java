package com.ajaya.cashloan.cl.model.excel;

import java.io.File;
import java.util.List;
import java.util.Map;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {

	/**
	 * java导出excel方法
	 * 
	 * @param list
	 *            表内容数据
	 * @param sheetName
	 *            第0个表工作名称
	 * @param columns
	 *            表头数据
	 * @return flag
	 */
	public static int exportExcel(List<Map<String, Object>> list1, List<Map<String, Object>> list2,  String sheetName, List<String> columns1, List<String> columns2) {
		int flag = 0;
		// 声明工作簿jxl.write.WritableWorkbook
		WritableWorkbook wwb;
		try {
			// 在本地创建一个excel文件，如果文件存在每次都会生成新的覆盖原来的文件，
			// 这样就永远只生成一个excel，而不占用服务器太多空间
			File file = new File("/data/email/" + sheetName + ".xls");
			if (!file.exists()) {
				file.createNewFile();
			}
			wwb = Workbook.createWorkbook(file);
			/*
			 * 创建一个工作表、sheetName为工作表的名称、"0"为第一个工作表
			 * 打开Excel的时候会看到左下角默认有3个sheet、"sheet1、sheet2、sheet3"这样
			 * 代码中的"0"就是sheet1、其它的一一对应。 createSheet(sheetName,
			 * 0)一个是工作表的名称，另一个是工作表在工作薄中的位置
			 */
			WritableSheet ws = wwb.createSheet("逾期数据", 0);
			createSheet(ws, columns1, list1, wwb);
			
			WritableSheet ws1 = wwb.createSheet("规则通过率数据", 1);
			createSheet(ws1, columns2, list2, wwb);
			
			
			// 写入Exel工作表
			wwb.write();

			// 关闭Excel工作薄对象
			wwb.close();
			
		} catch (IllegalStateException e) {
			System.err.println(e.getMessage());
		} catch (Exception ex) {
			flag = 0;
			ex.printStackTrace();
		}

		return flag;
	}

	public static Integer createSheet(WritableSheet ws, List<String> columns, List<Map<String, Object>> list, WritableWorkbook wwb) throws Exception {

		int flag = 0;

		SheetSettings ss = ws.getSettings();
		ss.setVerticalFreeze(1);// 冻结表头

		WritableFont font1 = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
		WritableFont font2 = new WritableFont(WritableFont.createFont("微软雅黑"), 9, WritableFont.NO_BOLD);
		WritableCellFormat wcf = new WritableCellFormat(font1);
		WritableCellFormat wcf2 = new WritableCellFormat(font2);
		WritableCellFormat wcf3 = new WritableCellFormat(font2);// 设置样式，字体

		// 创建单元格样式
		// WritableCellFormat wcf = new WritableCellFormat();

		// 背景颜色
		wcf.setBackground(jxl.format.Colour.YELLOW);
		wcf.setAlignment(Alignment.CENTRE); // 平行居中
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
		wcf3.setAlignment(Alignment.CENTRE); // 平行居中
		wcf3.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
		wcf3.setBackground(Colour.LIGHT_ORANGE);
		wcf2.setAlignment(Alignment.CENTRE); // 平行居中
		wcf2.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中

		/*
		 * 这个是单元格内容居中显示 还有很多很多样式
		 */
		wcf.setAlignment(Alignment.CENTRE);

		// 判断一下表头数组是否有数据
		if (columns != null && columns.size() > 0) {

			// 循环写入表头
			for (int i = 0; i < columns.size(); i++) {

				/*
				 * 添加单元格(Cell)内容addCell() 添加Label对象Label()
				 * 数据的类型有很多种、在这里你需要什么类型就导入什么类型 如：jxl.write.DateTime
				 * 、jxl.write.Number、jxl.write.Label Label(i, 0, columns[i],
				 * wcf) 其中i为列、0为行、columns[i]为数据、wcf为样式
				 * 合起来就是说将columns[i]添加到第一行(行、列下标都是从0开始)第i列、样式为什么"色"内容居中
				 */
				ws.addCell(new Label(i, 0, columns.get(i), wcf));
			}

			// 判断表中是否有数据
			if (list != null && list.size() > 0) {
				// 循环写入表中数据
				int i = 0;
				for (Map<String, Object> map : list) {
					// 括号中第一个参数代表列，第二个参数代表行，第三个参数代表内容
					// 下面第一个也就是第0列的第1行（第0行做了表头）的内容为userName。
					/*
					 * ws.addCell(new Label(0,i+1, map.get("aaa").toString()));
					 * ws.addCell(new Label(1,i+1, map.get("bbb").toString()));
					 * ws.addCell(new Label(2,i+1, map.get("ccc").toString()));
					 * i++;
					 */
					int j = 0;
					/*
					 * for (Map.Entry<String, Object> entry : map.entrySet()) {
					 * j++; ws.addCell(new Label(j - 1 ,1 + i,
					 * entry.getValue().toString())); }
					 */

					for (int m = 0; m < columns.size(); m++) {
						j++;
						ws.addCell(new Label(j - 1, 1 + i, map.get(columns.get(m)).toString()));
					}

					i++;
				}
			} else {
				flag = -1;
			}
		
		}
		return flag;
	}

	/*public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map1 = new HashMap<>();
		map1.put("aaa", "AAA");
		map1.put("bbb", "BBB");
		map1.put("ccc", "CCC");

		Map<String, Object> map2 = new HashMap<>();
		map2.put("aaa", "AAA2");
		map2.put("bbb", "BBB2");
		map2.put("ccc", "CCC2");

		list.add(map1);
		list.add(map2);

		List<String> columns = new ArrayList<>();
		columns.add("bb");
		columns.add("cc");
		columns.add("dd");
		columns.add("ee");
		exportExcel(list, "aa", columns);
	}*/
}
