package com.demo.util;

import com.demo.mail.MailEngine;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class BuildHtmlUtil {
    protected static final Log log = LogFactory.getLog(BuildHtmlUtil.class);
    private static String dir = "classpath:template/template.html";
    private static String dest = "E:\\file/template.pdf";
    private FreeMarkerConfigurer freeMarkerConfigurer;
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

    public static void createPdfTemplate(String text, HttpServletRequest request,String fileName) throws Exception {
        File workPath=new File( request.getSession().getServletContext().getRealPath("/")+"work");
        if(!workPath.exists()){
            workPath.mkdirs();
        }
        File f = new File(workPath,fileName);
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


    public static void createPDF(File sour,String dest) throws Exception {

        if(sour == null){
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
                new FileInputStream(sour), Charset.forName("UTF-8"));
        // step 5
        document.close();
    }

}
