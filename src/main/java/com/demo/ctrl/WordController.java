package com.demo.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.mail.MailEngine;
import com.demo.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
@Controller
@RequestMapping("/word")
public class WordController {
    @Autowired
    private MailEngine mailEngine;

    @RequestMapping("/submit")
    public String submit(HttpServletRequest request,String data,String template,boolean send,String email){
        Map<String,String> dataMap=new HashMap<String, String>();

        JSONObject jsonObject;
        try{

            jsonObject = JSON.parseObject(data);
        }catch (Exception e){
            request.setAttribute("result","failure");
            e.printStackTrace();
            return "submitresult.jsp";
        }

        String templatePath= request.getSession().getServletContext().getRealPath("/")+"template";
        File workPath=new File( request.getSession().getServletContext().getRealPath("/")+"work");
        if(!workPath.exists()){
            workPath.mkdirs();
        }
        String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".doc";

        WordService.createWord(jsonObject,templatePath,template,workPath,fileName);

        if(!send){
            return "redirect:/word/download?filePath="+workPath.getAbsolutePath()+File.separator+fileName;
        }

        try{
            mailEngine.sendEmail(email,"test","","test",workPath.getAbsolutePath()+File.separator+fileName);
            request.setAttribute("result","success");
            return "submitresult.jsp";
        }catch(Exception e){
            request.setAttribute("result","failure");
            e.printStackTrace();
            return "submitresult.jsp";
        }
    }

    @RequestMapping(value="/download")
    public void download(HttpServletRequest request, HttpServletResponse response)throws Exception{
        File file=new File(request.getParameter("filePath"));
        InputStream input=new FileInputStream(file);
        response.reset(); // 必要地清除response中的缓存信息
        response.setHeader("Content-Disposition", "attachment; filename=" +file.getPath().substring(file.getPath().lastIndexOf(File.separator)+1));
        OutputStream output = response.getOutputStream();

        byte[] content = new byte[input.available()];
        int length = 0;
        while ((length = input.read(content)) != -1) {
            output.write(content, 0, length);
        }

        output.write(content);
        output.flush();
        output.close();
    }

    @RequestMapping("/batch")
    @ResponseBody
    public String batch(HttpServletRequest request,String data,String template,String fileName){
        fileName=fileName.trim();
        if(fileName.indexOf(".")>0) {
            fileName = fileName.substring(0, fileName.indexOf("."));
        }

        JSONArray array=JSON.parseArray(data);

        String templatePath= request.getSession().getServletContext().getRealPath("/")+"template";
        File workPath=new File( request.getSession().getServletContext().getRealPath("/")+"work");
        if(!workPath.exists()){
            workPath.mkdirs();
        }

        try{
            for(Object object:array){
                JSONObject temp=(JSONObject)object;
                String filePath=fileName+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".doc";
                WordService.createWord(temp,templatePath,template,workPath,filePath);
                mailEngine.sendEmail(temp.getString("email"),"test","","test",workPath.getAbsolutePath()+File.separator+filePath);
            }
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

    }

    @RequestMapping(value="/upload",method= RequestMethod.POST)
    public String upload(HttpServletRequest req) throws Exception{
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)req;
        MultipartFile file = mreq.getFile("file");
        String fileName = file.getOriginalFilename();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        File path=new File(req.getSession().getServletContext().getRealPath("/")+"template");
        if(!path.exists()){
            path.mkdir();
        }
        String aim=path.getAbsolutePath()+ File.separator+fileName.substring(0,fileName.lastIndexOf("."))+sdf.format(new Date())+fileName.substring(fileName.lastIndexOf('.'));
        FileOutputStream fos = new FileOutputStream(aim);
        fos.write(file.getBytes());
        fos.flush();
        fos.close();
        req.setAttribute("result","success");
        return "uploadresult.jsp";
    }


    @RequestMapping(value = "/viewTemplate")
    @ResponseBody
    public String viewTemplates(HttpServletRequest req){
        File parent=new File(req.getSession().getServletContext().getRealPath("/template"));
        File[] templates=parent.listFiles();
        String fileNames="";
        for(File file:templates){
            if(file.getName().endsWith(".ftl")){
                fileNames+=","+file.getName();
            }
        }
        return fileNames.substring(1);
    }
}
