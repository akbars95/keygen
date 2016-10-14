package com.mtsmda.keygen.desktop.service;

import com.mtsmda.generator.GenerateRandom;
import com.mtsmda.helper.*;
import com.mtsmda.keygen.desktop.configuration.FreeMarkerFacade;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by dminzat on 9/22/2016.
 */
@Service("mailServiceImpl")
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class);

    @Autowired
    @Qualifier("mailSender")
    private JavaMailSenderImpl mailSender;

    @Autowired
    @Qualifier("freeMarkerFacade")
    private FreeMarkerFacade freeMarkerFacade;

    /**
     * This method will send compose and send the message
     */
    @Override
    public CommonResponse<Boolean> sendEMail(String to, String subject, String body) {
//        return sendEmail(to, subject, body, null);
        return sendEmailMessage(to, subject, body, null, null);
    }

    @Override
    public CommonResponse<Boolean> sendEMailViaFreemarkerTemplate(String to, String subject, Map<String, Object> input) {
        return sendEmailMessage(to, subject, null, input, null);
    }

    @Override
    public CommonResponse<Boolean> sendEmail(String to, String subject, String body, String[] attachFiles) {
        return sendEmailMessage(to, subject, body, null, attachFiles);
//        this.mailSender.getJavaMailProperties().put("mail.smtp.host", "smtp.gmail.com");
//        this.mailSender.getJavaMailProperties().put("mail.smtp.port", "587");
        /*Session session = Session.getInstance(this.mailSender.getJavaMailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSender.getUsername(),
                        mailSender.getPassword());
            }
        });

        *//*javax.mail.Message*//*
        MimeMessage messageMail = new MimeMessage(session);*/

        /*SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("souvenir.buy.site@gmail.com");
        simpleMailMessage.setTo("souvenir.buy.site@gmail.com");
        simpleMailMessage.setSubject(message.getMessageName());*/

        /*Template template = velocityEngine.getTemplate("./velocity.template/email_template.vm");//


        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("emailWriter", message.getMessageName());
        velocityContext.put("emailWriterText", message.getMessageText());
        velocityContext.put("emailWriterEmailAddress", message.getMessageEmail());
        velocityContext.put("serverDateTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

        StringWriter stringWriter = new StringWriter();
        template.merge(velocityContext, stringWriter);*/
        /*message.setMessageText("fsdfsdfsdfsd");*/

        /*try {
            messageMail.setFrom(new InternetAddress("souvenir.buy.site@gmail.com"));
            messageMail.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
            messageMail.setSubject(subject);
            messageMail.setText(body, "UTF-8", "html");
            messageMail.setSentDate(new Date());

            BodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent("dsfsdfsd", "text/html");

            Multipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mimeBodyPart);

            if (attachFiles != null && attachFiles.length > 0) {
                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    try {
                        attachPart.attachFile(filePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    mimeMultipart.addBodyPart(attachPart);
                }
            }

            messageMail.setContent(mimeMultipart);
//            mailSender.send(simpleMailMessage);
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(messageMail);
            Transport.send(messageMail);
        } catch (MailException | MessagingException e) {
            //https://www.google.com/settings/security/lesssecureapps - fix
            return new CommonResponse<>(false, 0, null);
        }
        return new CommonResponse<>(true, 0, null);*/
    }

    @Override
    public CommonResponse<Boolean> sendEmailViaFreemarkerTemplate(String to, String subject, Map<String, Object> input, String[] attachFiles) {
        return sendEmailMessage(to, subject, null, input, attachFiles);
    }

    private CommonResponse<Boolean> sendEmailMessage(String to, String subject, String body, Map<String, Object> input, String[] attachFiles) {
        String innerMessageForLog = null;
        try {
            if (ObjectHelper.objectIsNull(to)) {
                innerMessageForLog = "param 'to' is null or empty!";
                LOGGER.error(innerMessageForLog);
                return new CommonResponse<>(false, CommonResponse.EMAIL_SERVICE_ERROR, innerMessageForLog);
            }

            if (ObjectHelper.objectIsNull(subject)) {
                innerMessageForLog = "param 'subject' is null or empty!";
                LOGGER.error(innerMessageForLog);
                return new CommonResponse<>(false, CommonResponse.EMAIL_SERVICE_ERROR, innerMessageForLog);
            }

            if (ObjectHelper.objectIsNull(body) && ObjectHelper.objectIsNull(input)) {
                innerMessageForLog = "param 'body' or 'input' is null or empty!";
                LOGGER.error(innerMessageForLog);
                return new CommonResponse<>(false, CommonResponse.EMAIL_SERVICE_ERROR, innerMessageForLog);
            }

            Session session = Session.getInstance(this.mailSender.getJavaMailProperties(), new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailSender.getUsername(),
                            mailSender.getPassword());
                }
            });

            /*Configuration configuration = new Configuration(new Version(2, 3, 23));
            configuration.setClassForTemplateLoading(StaticPageController.class, "/freemarker/");
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);*/
            Writer writer = new StringWriter();
//            Template template = null;
            try {
                freeMarkerFacade.writeContent("emailSend.ftl", writer, input);
                /*template = configuration.getTemplate("emailSend.ftl",
                        "UTF-8");
                template.process(input, writer);*/
            } catch (Exception e) {
                e.printStackTrace();
            }

            MimeMessage messageMail = new MimeMessage(session);
            messageMail.setFrom(new InternetAddress(mailSender.getUsername()));
            messageMail.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
            messageMail.setSubject(subject);
            messageMail.setText(writer.toString(), "UTF-8", "html");
            messageMail.setSentDate(LocalDateTimeHelper.convertCurrentLocalDateTimeToDate());

            BodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(writer.toString(), "text/html");

            writer.close();

            Multipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mimeBodyPart);

            if (ObjectHelper.objectIsNotNull(attachFiles) && attachFiles.length > 0) {
                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    try {
                        attachPart.attachFile(filePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    mimeMultipart.addBodyPart(attachPart);
                }
            }

            messageMail.setContent(mimeMultipart);
            Transport.send(messageMail);
            return new CommonResponse<>(true, CommonResponse.EMAIL_SERVICE_SUCCESS, null);
        } catch (Exception e) {
            innerMessageForLog = ExceptionMessageHelper.exceptionDescription(e);
            LOGGER.error(innerMessageForLog);
            return new CommonResponse<>(false, CommonResponse.EMAIL_SERVICE_ERROR, innerMessageForLog);
        }
    }

    public MimeMessagePreparator getContentWithAttachments(String from, String[] to, String subject, String content, Map<String, ClassPathResource> fileNameFileMapInlineImages, Map<String, ClassPathResource> fileNameFileMap) {
        return mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, true);
            if (ObjectHelper.objectIsNotNull(fileNameFileMapInlineImages) && !fileNameFileMapInlineImages.isEmpty()) {
                fileNameFileMapInlineImages.forEach((name, classPathResource) -> {
                    try {
                        mimeMessageHelper.addInline(name, classPathResource);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                });
            }
            if (ObjectHelper.objectIsNotNull(fileNameFileMap) && !fileNameFileMap.isEmpty())
                fileNameFileMap.forEach((fileName, classPathResource) -> {
                    try {
                        mimeMessageHelper.addAttachment(fileName, classPathResource);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                });
        };
    }

    public void sendEmailWithAttachment() {
        Writer writer = new StringWriter();
        ClassPathResource classPathResource = new ClassPathResource("images/email/Screenshot.png");
        Map<String, Object> stringObjectMap = null;
        try {
            stringObjectMap = MapHelper.getMap(
                    ListHelper.getListWithData("currentUser", "requestDateTime",
                            "tempUrl", "tempPassword", "imageSrc"),
                    ListHelper.getListWithData("Ivanov Ivan", LocalDateTimeHelper.localDateTime(LocalDateTime.now(), LocalDateTimeHelper.NORMAL_DATE_TIME_FORMAT),
                            "http://yandex.ru", new GenerateRandom(true).generate(19), "firstImage"));

            System.out.println(classPathResource.getPath());
            this.freeMarkerFacade.writeContent("emailSendWithImage.ftl", writer, stringObjectMap);
            Map<String, ClassPathResource> stringClassPathResourceMapInlineImages = MapHelper.getMap(
                    ListHelper.getListWithData("firstImage"), ListHelper.getListWithData(classPathResource)
            );
            Map<String, ClassPathResource> stringClassPathResourceMap = MapHelper.getMap(
                    ListHelper.getListWithData("description"), ListHelper.getListWithData(classPathResource)
            );
            this.mailSender.send(getContentWithAttachments("souvenir.buy.site@gmail.com", new String[]{"artem.borisov0922@yandex.ru"},
                    "testing", writer.toString(), stringClassPathResourceMapInlineImages, stringClassPathResourceMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(LocalDate.of(2015, 10, 22).plusDays(180).plusDays(180));
    }

}