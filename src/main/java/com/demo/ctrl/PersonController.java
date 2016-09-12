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
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
@RestController()
@RequestMapping(value = "")
public class PersonController {

    @Resource
    private SimpleMailMessage mailMessage;
    @Resource
    private MailEngine mailEngine;

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Object test(@RequestBody JSONObject jsonObject) throws Exception {
        BuildHtmlUtil.createPdfTemplate(htmlTemplate2String(jsonObject));
        BuildHtmlUtil.createPDF();
        return jsonObject;
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
//        mailEngine.send(mailMessage,"text.ftl",model);

        File f = new File("E:\\file/test.txt");
        try {
//            mailEngine.sendMessage("qdlgxiemaosheng@163.com","407922583@qq.com","test","test","test",f);
//            mailEngine.sendEmail("1815111692@qq.com","test");
            mailEngine.sendEmail("407922583@qq.com","test","xms","test","E:\\file/test.txt");
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
