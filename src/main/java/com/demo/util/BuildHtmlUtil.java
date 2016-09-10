package com.demo.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class BuildHtmlUtil {
    private static String fileName = "";
    private static String dir = "classpath:template/template.html";
    private static String dest = "E:\\file/template.pdf";

    public static void createPdfTemplate(String text) throws Exception {
        File f = null;
        if(isExistFile(dir)){
            f = ResourceUtils.getFile(dir);
        }else {
            throw new Exception("文件模板不存在");
        }
        PrintStream ps = new PrintStream(f);
        ps.println(text);
        ps.close();
    }

    private static boolean isExistFile(String dir) throws Exception {
        File f = ResourceUtils.getFile(dir);
        if (!f.exists()) {
           return false;
        }
        return true;
    }

//    public static void createPDF(String dest, String htmlFile) throws Exception {
//        File f = null;
//        if(isExistFile(dir)){
//            f = ResourceUtils.getFile(dir);
//        }else {
//            throw new Exception("文件模板不存在");
//        }
//
//        // step 1
//        Document document = new Document();
//        // step 2
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
//        // step 3
//        document.open();
//        // step 4
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
//                new FileInputStream(f), Charset.forName("UTF-8"));
//        // step 5
//        document.close();
//    }

    public static void createPDF() throws Exception {
        File f = null;
        if(isExistFile(dir)){
            f = ResourceUtils.getFile(dir);
        }else {
            throw new Exception("文件模板不存在");
        }
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(f), Charset.forName("UTF-8"));
        // step 5
        document.close();
    }

}
