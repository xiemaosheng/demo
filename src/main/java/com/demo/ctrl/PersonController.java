package com.demo.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.demo.domain.Person;
import com.demo.util.BuildHtmlUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/4 0004.
 */
@RestController()
@RequestMapping(value = "")
public class PersonController {

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Object Test(@RequestBody JSONObject jsonObject) throws Exception{
        BuildHtmlUtil.createPdfTemplate(htmlTemplate2String(jsonObject));
        BuildHtmlUtil.createPDF();
        return jsonObject;
    }

    public String htmlTemplate2String(JSONObject jsonObject){
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
}
