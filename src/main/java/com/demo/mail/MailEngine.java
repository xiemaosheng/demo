package com.demo.mail;

import com.demo.domain.Property;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2016/9/11 0011.
 */
@Component
public class MailEngine {
    protected static final Log log = LogFactory.getLog(MailEngine.class);
    @Resource
    private JavaMailSenderImpl mailSender;
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Resource
    private Property property;

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public FreeMarkerConfigurer getFreeMarkerConfigurer() {
        return freeMarkerConfigurer;
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public String generateEmailContent(String templateName, Map map) {
        try {

            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            //设置编码
            configuration.setDefaultEncoding("UTF-8");

            Template t = configuration.getTemplate(templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
        } catch (TemplateException e) {
            log.error("Error while processing FreeMarker template ", e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("Error while open template file ", e);
        } catch (IOException e) {
            log.error("Error while generate Email Content ", e);
        }
        return null;
    }

    /**
     * 发送邮件
     *
     * @param emailAddresses   收件人Email地址的数组
     * @param fromEmail      寄件人Email地址, null为默认寄件人web@vnvtrip.com
     * @param bodyText       邮件正文
     * @param subject        邮件主题
     * @param attachmentName 附件名
     * @param file       附件
     * @throws MessagingException
     */

    public void sendMessage(String emailAddresses, String fromEmail,
                            String bodyText, String subject, String attachmentName,
                            File file) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailAddresses);
        if (fromEmail != null) {
            helper.setFrom(fromEmail);
        }
        helper.setText(bodyText, true);
        helper.setSubject(subject);

        if (attachmentName != null && file != null)
            helper.addAttachment(attachmentName, file);

        mailSender.send(message);
    }


    /**
     * 发送简单邮件
     *
     * @param msg
     */
    public void send(SimpleMailMessage msg) {
        try {
            ((JavaMailSenderImpl) mailSender).send(msg);
        } catch (MailException ex) {
            //log it and go on
            log.error(ex.getMessage());
        }
    }

    /**
     * 使用模版发送HTML格式的邮件
     *
     * @param msg          装有to,from,subject信息的SimpleMailMessage
     * @param templateName 模版名,模版根路径已在配置文件定义于freemakarengine中
     * @param model        渲染模版所需的数据
     */
    public void send(SimpleMailMessage msg, String templateName, Map model) {
        //生成html邮件内容
        String content = generateEmailContent(templateName, model);
        System.out.println(content);
        MimeMessage mimeMsg = null;
        try {
            mimeMsg = ((JavaMailSenderImpl) mailSender).createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
            helper.setTo(msg.getTo());

            if (msg.getSubject() != null)
                helper.setSubject(msg.getSubject());

            if (msg.getFrom() != null)
                helper.setFrom(msg.getFrom());

            helper.setText(content, true);

            ((JavaMailSenderImpl) mailSender).send(mimeMsg);
        } catch (MessagingException ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    public void sendEmail(String sendAddress, String content,String sender,String title,String path)
            throws Exception {

        // 创建一个连接属性。
        Properties props = new Properties(); //
        props.put("mail.smtp.host ", property.getHost()); // 设置smtp的服务器地址是smtp.126.com
        props.put("mail.smtp.auth", "true"); // 设置smtp服务器要身份验证。
        props.put("mail.transport.protocol", "stmp");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(property.getUsername(), property.getPassword());
            }
        });
        Message message = new MimeMessage(session);
        InternetAddress from = new InternetAddress(property.getAddress()); // 发送人地址
        from.setPersonal(MimeUtility.encodeText(sender)); // 发件人名
        message.setFrom(from);
        // InternetAddress to = new InternetAddress("taozhida11@sina.cn");
        // //收件人地址

        // 创建邮件体:
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(sendAddress));// 抄送给多个人的邮箱
        // message.setRecipient(Message.RecipientType.TO, to); // 只有抄送给自己的邮箱
        message.setSubject(MimeUtility.encodeText(title)); // 邮件标题
        message.setSentDate(new Date());
        MimeMultipart msgMultipart = new MimeMultipart("mixed");// 指定为混合关系
        message.setContent(msgMultipart);
        // 邮件内容
        MimeBodyPart htmlPart = new MimeBodyPart();
        // TODO 组装的顺序非常重要，一定要先组装文本域，再组装文件
        msgMultipart.addBodyPart(htmlPart);
        // 组装附件
         MimeBodyPart file = new MimeBodyPart();
         FileDataSource file_datasource = new FileDataSource(path);
         DataHandler dh = new DataHandler(file_datasource);
         file.setDataHandler(dh);
         // 附件区别内嵌内容的一个特点是有文件名，为防止中文乱码要编码
         file.setFileName(MimeUtility.encodeText(dh.getName()));
         msgMultipart.addBodyPart(file);
        message.saveChanges();

        // 发送邮件的过程:95188
        Transport transport = session.getTransport("smtp"); // 创建连接
        transport.connect(property.getHost(), 25, property.getUsername(), property.getPassword()); // 连接服务器

        transport.sendMessage(message, message.getAllRecipients()); // 发送信息
        transport.close(); // 关闭
        System.out.println("发送完毕");

    }

    public static void sendEmail(String sendAddress, String content)
            throws Exception {

        // 创建一个连接属性。
        Properties props = new Properties(); //
        props.put("mail.smtp.host ", "smtp.163.com"); // 设置smtp的服务器地址是smtp.126.com
        props.put("mail.smtp.auth", "true"); // 设置smtp服务器要身份验证。
        props.put("mail.transport.protocol", "stmp");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("qdlgxiemaosheng@163.com", "abc654321");
            }
        });
        Message message = new MimeMessage(session);
        InternetAddress from = new InternetAddress("qdlgxiemaosheng@163.com"); // 发送人地址
        from.setPersonal(MimeUtility.encodeText("xms")); // 发件人名
        message.setFrom(from);
        // InternetAddress to = new InternetAddress("taozhida11@sina.cn");
        // //收件人地址

        // 创建邮件体:
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(sendAddress));// 抄送给多个人的邮箱
        // message.setRecipient(Message.RecipientType.TO, to); // 只有抄送给自己的邮箱
        message.setSubject(MimeUtility.encodeText("test")); // 邮件标题
        message.setSentDate(new Date());
        MimeMultipart msgMultipart = new MimeMultipart("mixed");// 指定为混合关系
        message.setContent(msgMultipart);
        // 邮件内容
        MimeBodyPart htmlPart = new MimeBodyPart();
        msgMultipart.addBodyPart(htmlPart);
        message.saveChanges();

        // 发送邮件的过程:95188
        Transport transport = session.getTransport("smtp"); // 创建连接
        transport.connect("smtp.163.com", 25, "qdlgxiemaosheng@163.com", "abc654321"); // 连接服务器
        // 服务名，端口，发送邮件用户名
        // （不要@后面），密码
        transport.sendMessage(message, message.getAllRecipients()); // 发送信息
        transport.close(); // 关闭
        System.out.println("发送完毕");

    }


}

