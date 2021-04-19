package com.ajaya.cashloan.core.common.util;

import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

public class ImgToPdfToBase64Uil {
	private final static Logger logger = LoggerFactory.getLogger(ImgToPdfToBase64Uil.class);
	public static String toBase64(String imgPath) {
		String base64Str = "";
		try {
			Document doc = new Document(PageSize.A4, 20, 20, 20, 20); // new一个pdf文档
			String pafPath = "/data/www/temp.pdf";
			PdfWriter.getInstance(doc, new FileOutputStream(pafPath )); // pdf写入
			doc.open();// 打开文档
			doc.newPage(); // 在pdf创建一页
			Image png1 = Image.getInstance(imgPath); // 通过文件路径获取image
			float heigth = png1.getHeight();
			float width = png1.getWidth();
			int percent = getPercent2(heigth, width);
			png1.setAlignment(Image.MIDDLE);
			png1.scalePercent(percent + 3);// 表示是原来图像的比例;
			doc.add(png1);
			doc.close();
			System.out.println("生成文件结束");
			File file = new File(pafPath);
			// 生成base64
			base64Str = FreeMarkerToPdfUtils.pdfToBase64Str(file);
			boolean delete = file.delete();
			System.out.println("删除文件结束," + delete);
		} catch (Exception e) {
			logger.info("imgPath:" + imgPath + ",图片生成pdf转换尘封base64报错," + e);
		}
		return base64Str;
	}

	public static int getPercent(float h, float w) {
		int p = 0;
		float p2 = 0.0f;
		if (h > w) {
			p2 = 297 / h * 100;
		} else {
			p2 = 210 / w * 100;
		}
		p = Math.round(p2);
		return p;
	}

	public static int getPercent2(float h, float w) {
		int p = 0;
		float p2 = 0.0f;
		p2 = 530 / w * 100;
		p = Math.round(p2);
		return p;
	}
}
