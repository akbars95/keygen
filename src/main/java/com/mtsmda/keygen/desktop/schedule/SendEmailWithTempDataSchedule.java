package com.mtsmda.keygen.desktop.schedule;

import com.mtsmda.generator.GenerateRandom;
import com.mtsmda.helper.ListHelper;
import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.helper.MapHelper;
import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.configuration.AppConfiguration;
import com.mtsmda.keygen.desktop.model.Status;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserKey;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.repository.StatusRepository;
import com.mtsmda.keygen.desktop.repository.UserKeyRepository;
import com.mtsmda.keygen.desktop.repository.UserRepository;
import com.mtsmda.keygen.desktop.repository.UserRequestRepository;
import com.mtsmda.keygen.desktop.service.GenerateKeyService;
import com.mtsmda.keygen.desktop.service.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.mtsmda.helper.ObjectHelper.objectIsNotNull;

/**
 * Created by dminzat on 9/22/2016.
 */
@Component("sendEmailWithTempDataSchedule")
public class SendEmailWithTempDataSchedule {

    private static final Logger LOGGER = Logger.getLogger(SendEmailWithTempDataSchedule.class);

    @Autowired
    @Qualifier("userRequestRepositoryImpl")
    private UserRequestRepository userRequestRepository;

    @Autowired
    @Qualifier("mailServiceImpl")
    private MailService mailService;

    @Autowired
    @Qualifier("generateKeyServiceImpl")
    private GenerateKeyService generateKeyService;

    @Autowired
    @Qualifier("userKeyRepositoryImpl")
    private UserKeyRepository userKeyRepository;

    @Autowired
    @Qualifier("userRepositoryImpl")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("appConfiguration")
    private AppConfiguration appConfiguration;

    public void executeJobSendEmailWithTempData() {
        LOGGER.info("executeJobSendEmailWithTempData - - ");
        CommonResponse<List<UserRequest>> userRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword = userRequestRepository.getUserRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword();
        if (objectIsNotNull(userRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword) && ListHelper.listIsNotNullAndNotEmpty(userRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword.getObject())) {
            LOGGER.info(userRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword + " success");

            userRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword.getObject().forEach(userRequest -> {
                CommonResponse<User> userByUserId = userRepository.getUserByUserId(userRequest.getUser().getUserId());
                if (ObjectHelper.objectIsNull(userByUserId) || ObjectHelper.objectIsNull(userByUserId.getObject())) {
                    LOGGER.info(userByUserId);
                    return;
                }
                userRequest.setUser(userByUserId.getObject());
                Map<String, Object> input = MapHelper.getMap(ListHelper.getListWithData("currentUser", "requestDateTime", "tempUrl", "tempPassword"),
                        ListHelper.getListWithData(userRequest.getUser().getUserName(),
                                LocalDateTimeHelper.localDateTime(userRequest.getRequestDateTime(), LocalDateTimeHelper.NORMAL_DATE_TIME_FORMAT),
                                appConfiguration.getApplicationContextPath() + "email/confirm/" + userRequest.getTempUrl(),
                                userRequest.getTempPassword()));
                CommonResponse<Boolean> booleanCommonResponse = mailService.sendEMailViaFreemarkerTemplate(userRequest.getUser().getUserEmail(), "Send temp link and password to " + userRequest.getUser().getUserName(), input);
                if (ObjectHelper.objectIsNotNull(booleanCommonResponse) && booleanCommonResponse.getObject()) {
                    CommonResponse<Boolean> booleanCommonResponse1 = userRequestRepository.changeCurrentStatus(userRequest);
                    if (ObjectHelper.objectIsNotNull(booleanCommonResponse1) && booleanCommonResponse1.getObject()) {
                        UserKey userKey = generateKeyService.generateKey();
                        userRequest.setUserKey(userKey);
                        userKey.setUserRequest(userRequest);
                        CommonResponse<Boolean> booleanCommonResponse2 = userKeyRepository.insertUserKey(userKey);
                        if (ObjectHelper.objectIsNull(booleanCommonResponse2) || !booleanCommonResponse2.getObject()) {
                            LOGGER.error(booleanCommonResponse2);
                        } else {
                            LOGGER.info(booleanCommonResponse2);
                        }
                    } else {
                        LOGGER.warn(booleanCommonResponse1);
                    }
                } else {
                    LOGGER.warn(booleanCommonResponse);
                }
            });
        }
        LOGGER.warn(userRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword);
    }

}