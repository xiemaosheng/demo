package com.demo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class BuildHtmlUtil {
    private static String fileName = "template.html";
    private static String dir = "e:/file";

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

}
