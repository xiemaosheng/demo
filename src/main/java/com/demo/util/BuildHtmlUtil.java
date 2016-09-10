package com.demo.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class BuildHtmlUtil {
    private static String fileName = "template.html";
    private static String dir = "e:/file";
    private static String dest = "e:/file/template.pdf";

    public static void createPdfTemplate(String text) throws Exception {
        createFile(dir, fileName);
//        String path = Thread.currentThread().getContextClassLoader().getResource("./template/template.html").getPath();
        PrintStream ps = new PrintStream(new FileOutputStream(dir + "/" + fileName));
        ps.println(text);

    }

    private static void createFile(String dir, String fileName) throws Exception {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(f, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public static void createPDF(String dest, String htmlFile) throws Exception {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(htmlFile), Charset.forName("UTF-8"));
        // step 5
        document.close();
    }

    public static void createPDF() throws Exception {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(dir + "/" + fileName), Charset.forName("UTF-8"));
        // step 5
        document.close();
    }

}
