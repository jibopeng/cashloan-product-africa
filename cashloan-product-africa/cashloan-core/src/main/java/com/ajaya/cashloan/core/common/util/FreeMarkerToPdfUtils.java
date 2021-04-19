package com.ajaya.cashloan.core.common.util;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

/**
 * 功能说明：
 *
 * @author yanzhiqiang
 * @since 2020-04-29 17:52
 **/
public class FreeMarkerToPdfUtils {
    private final static Logger logger = LoggerFactory.getLogger(FreeMarkerToPdfUtils.class);
    private static Configuration freemarkerCfg = null;
    static {
        freemarkerCfg = new Configuration();
        String webInf = PathUtil.getWEB_INF();
        //freemarker的模板目录
        try {
            freemarkerCfg.setDirectoryForTemplateLoading(new File(webInf + "views"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * freemarker渲染html
     *
     * @param data    模板需要数据map
     * @param htmlTmp 模板
     * @return html字符串
     */
    public static String freeMarkerRender(Map<String, Object> data, String htmlTmp) {
        Writer out = new StringWriter();
        try {
            // 获取模板,并设置编码方式
            Template template = freemarkerCfg.getTemplate(htmlTmp);
            template.setEncoding("utf-8");
            // 合并数据模型与模板
            //将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(data, out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }




    /**
     * 根据模板生成pdf文件流
     *
     * @param content 模板内容
     * @return 字节数组
     */
    public static ByteArrayOutputStream createPdf(String content) {
        String font = PathUtil.getWEB_INF() + "views/simhei.ttf";
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ITextRenderer render = new ITextRenderer();
        ITextFontResolver fontResolver = render.getFontResolver();
        try {
            fontResolver.addFont(font, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析html生成pdf
        render.setDocumentFromString(content);
        //解决图片相对路径的问题
      //  render.getSharedContext().setBaseURL(LOGO_PATH);
        render.layout();
        try {
            render.createPDF(outStream);
            return outStream;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将pdf转成base64字符串
     *
     * @param file 文件
     * @return StringBuffer
     */
    public static String pdfToBase64Str(File file) {
        FileInputStream is = null;
        ByteArrayOutputStream os = null;
        String dUrlData = "";
        //pdf源路径
        byte[] buff = new byte[1024];
        int len = 0;
        try {
            is = new FileInputStream(file);
            os = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            os.flush();
            os.toByteArray();
            dUrlData = Base64.encode(os.toByteArray());

        } catch (Exception ignored) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ignored) {
                }
            }
        }
        return dUrlData;
    }

    /***
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath pdf完整路径
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return
     */
    public static void pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
        File file = new File(PdfFilePath);
        PDDocument pdDocument;
        try {
            String imgPDFPath = file.getParent();
            int dot = file.getName().lastIndexOf('.');
            // 获取图片文件名
            String imagePDFName = file.getName().substring(0, dot);
            String imgFolderPath = null;
            if (dstImgFolder.equals("")) {
                // 获取图片存放的文件夹路径
                imgFolderPath = imgPDFPath + File.separator ;
            } else {
                imgFolderPath = dstImgFolder + File.separator ;
            }

            if (createDirectory(imgFolderPath)) {

                pdDocument = PDDocument.load(file);
                PDFRenderer renderer = new PDFRenderer(pdDocument);
				/* dpi越大转换后越清晰，相对转换速度越慢 */
                PdfReader reader = new PdfReader(PdfFilePath);
                int pages = reader.getNumberOfPages();
                StringBuffer imgFilePath = null;
                for (int i = 0; i < pages; i++) {
                    String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
                    imgFilePath = new StringBuffer();
                    imgFilePath.append(imgFilePathPrefix);
                    imgFilePath.append("_");
                    imgFilePath.append(String.valueOf(i + 1));
                    imgFilePath.append(".png");
                    File dstFile = new File(imgFilePath.toString());
                    if (!dstFile.exists()){
                        BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                        ImageIO.write(image, "png", dstFile);
                        logger.info("PDF文档转PNG图片成功！");
                    }else {
                        logger.info("PDF文档转PNG图片已经生成过了！");
                    }
                }


            } else {
                logger.info("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean createDirectory(String folder) {
        File dir = new File(folder);
        if (dir.exists()) {
            return true;
        } else {
            return dir.mkdirs();
        }
    }
}
