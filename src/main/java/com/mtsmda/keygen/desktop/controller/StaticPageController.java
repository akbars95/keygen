package com.mtsmda.keygen.desktop.controller;

import com.mtsmda.generator.GenerateRandom;
import com.mtsmda.helper.ListHelper;
import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.helper.MapHelper;
import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.service.FromEmailRequest;
import com.mtsmda.keygen.desktop.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MTSMDA on 13.09.2016.
 */
@Controller
public class StaticPageController {

    private static final ModelAndView modelAndView = new ModelAndView();

    @Autowired
    @Qualifier("mailServiceImpl")
    private MailService mailService;

    @Autowired
    @Qualifier("fromEmailRequestImpl")
    private FromEmailRequest fromEmailRequest;

    @RequestMapping(value = {"/", "/home", "/index"}, method = RequestMethod.GET)
    public ModelAndView indexPage() {
//        mailService.sendEmailWithAttachment();
        modelAndView.addObject("pageTitle", "Index page");
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerPage() {
        modelAndView.addObject("pageTitle", "Register page");
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/request_to_keygen", method = RequestMethod.GET)
    public ModelAndView requestToKeygen() {
        modelAndView.addObject("pageTitle", "Register page");
        modelAndView.setViewName("request_to_keygen");
        return modelAndView;
    }

    @RequestMapping(value = "/email/confirm/{tempURL}", method = RequestMethod.GET)
    public ModelAndView goFromEmail(@PathVariable String tempURL) {
        ModelAndView modelAndView = new ModelAndView();
        boolean hasError = false;
        if (StringUtils.isBlank(tempURL)) {
            hasError = goFromEmailHasError(modelAndView, "temp url is null or empty!");
        }
        CommonResponse<UserRequest> userRequestCommonResponse = fromEmailRequest.checkExistsTempUrl(tempURL);
        if (ObjectHelper.objectIsNull(userRequestCommonResponse) || ObjectHelper.objectIsNull(userRequestCommonResponse.getObject())) {
            hasError = goFromEmailHasError(modelAndView, "Not found temp url in database!");
        }
        if (hasError) {
            return modelAndView;
        }
        modelAndView.setViewName("temp_url");
        return modelAndView;
    }

    @RequestMapping(value = "/fileContent", method = RequestMethod.GET)
    public String fileContentsPage() {
        return "fileContents";
    }

    @RequestMapping(value = {"/404"}, method = RequestMethod.GET)
    public ModelAndView page404() {
//        mailService.sendEmailWithAttachment();
        modelAndView.setViewName("404");
        return modelAndView;
    }

    private boolean goFromEmailHasError(ModelAndView modelAndView, String messageText) {
        modelAndView.addObject("description", messageText);
        modelAndView.setViewName("404");
        return true;
    }

    /*public void emailSender() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUser(new User(null, null, "iavnoa", "124635"));
        userRequest.setRequestDateTime(LocalDateTime.now());
        userRequest.setTempUrl(new GenerateRandom(GenerateRandom.ENGLISH_LETTERS_AND_NUMBERS).generate(15));
        userRequest.setTempPassword(new GenerateRandom(GenerateRandom.ENGLISH_LETTERS_AND_NUMBERS).generate(25));

        Map<String, Object> input = MapHelper.getMap(ListHelper.getListWithData("currentUser", "requestDateTime", "tempUrl", "tempPassword"),
                ListHelper.getListWithData(userRequest.getUser().getUserName(),
                        LocalDateTimeHelper.localDateTime(userRequest.getRequestDateTime(),
                                LocalDateTimeHelper.NORMAL_DATE_TIME_FORMAT),
                        "email/confirm/" + userRequest.getTempUrl(),
                        userRequest.getTempPassword()));
        mailService.sendEMailViaFreemarkerTemplate("artem.borisov0922@yandex.ru", "Simple", input);
    }*/

}