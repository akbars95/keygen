package com.mtsmda.keygen.desktop.service;

import com.mtsmda.keygen.desktop.object.response.CommonResponse;

import java.util.Map;

/**
 * Created by dminzat on 9/22/2016.
 */
public interface MailService {

    public CommonResponse<Boolean> sendEMail(String to, String subject, String body);
    public CommonResponse<Boolean> sendEMailViaFreemarkerTemplate(String to, String subject, Map<String, Object> input);
    public CommonResponse<Boolean> sendEmail(String to, String subject, String body, String[] attachFiles);
    public CommonResponse<Boolean> sendEmailViaFreemarkerTemplate(String to, String subject, Map<String, Object> input, String[] attachFiles);
    void sendEmailWithAttachment();

}