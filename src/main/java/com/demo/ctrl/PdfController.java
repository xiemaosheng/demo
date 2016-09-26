package com.demo.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.demo.domain.User;
import com.demo.mail.MailEngine;
import com.demo.util.BuildHtmlUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
@RestController()
@RequestMapping(value = "")
public class PdfController {
    @Resource
    private SimpleMailMessage mailMessage;
    @Resource
    private MailEngine mailEngine;

    @RequestMapping(value = "/pdf/submit")
    public String test(HttpServletRequest request, String data, String template, boolean send, String email) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(request.getParameter("data"));
        String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".html";
        String des = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".pdf";
        File workPath=new File( request.getSession().getServletContext().getRealPath("/")+"work");
        if(!workPath.exists()){
            workPath.mkdirs();
        }
        File f = new File(workPath,fileName);
        PrintStream ps = new PrintStream(f);
        String text = mailEngine.generateEmailContent(template,jsonObject);
        ps.println(text);
        System.out.println(mailEngine.generateEmailContent(template,jsonObject));
        BuildHtmlUtil.createPDF(f,request.getSession().getServletContext().getRealPath("/")+"work/"+des);
        ps.close();
        if(!send){
            return "redirect:"+ request.getSession().getServletContext().getRealPath("/") +"/word/download?filePath="+workPath.getAbsolutePath()+File.separator+des;
        }

        try{
            mailEngine.sendEmail(email,"test","","test",workPath.getAbsolutePath()+File.separator+des);
            request.setAttribute("result","success");
            return "submitresult.jsp";
        }catch(Exception e){
            request.setAttribute("result","failure");
            return "submitresult.jsp";
        }
    }


    public String htmlTemplate2String(JSONObject jsonObject) {
        StringBuilder text = new StringBuilder();
        text.append("<html>");
        text.append("<title>test</title>");
        text.append("<body>");
        text.append("<table width=”450″ border=”1″ cellspacing=”0″ cellpadding=”2″ bordercolor=”#009900″>");
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            text.append("<tr>");
            text.append("<td>" + key + "</td>");
            text.append("<td>" + jsonObject.get(key) + "</td>");
            text.append("</tr>");
        }
        text.append("</table>");
        text.append("</body>");
        text.append("</html>");
        return text.toString();
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Object send(@RequestBody User user){
        Map<String,User> model = new HashMap<String , User>();
        model.put("user",user);
        mailEngine.send(mailMessage,"text.ftl",model);

//        File f = new File("E:\\file/test.txt");
//        try {
////            mailEngine.sendMessage("qdlgxiemaosheng@163.com","407922583@qq.com","test","test","test",f);
//            mailEngine.sendEmail("1815111692@qq.com","test");
////            mailEngine.sendEmail("407922583@qq.com","test","xms","test","E:\\file/test.txt");
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return null;
    }
}
