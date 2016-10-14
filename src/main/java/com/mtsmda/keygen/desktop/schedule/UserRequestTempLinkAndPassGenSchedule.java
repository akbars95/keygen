package com.mtsmda.keygen.desktop.schedule;

import com.mtsmda.generator.GenerateRandom;
import com.mtsmda.helper.ListHelper;
import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.Status;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.repository.StatusRepository;
import com.mtsmda.keygen.desktop.repository.UserRequestRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mtsmda.helper.ObjectHelper.*;

/**
 * Created by dminzat on 9/22/2016.
 */
@Component("userRequestSchedule")
public class UserRequestTempLinkAndPassGenSchedule {

    private static final Logger LOGGER = Logger.getLogger(UserRequestTempLinkAndPassGenSchedule.class);

    @Autowired
    @Qualifier("userRequestRepositoryImpl")
    private UserRequestRepository userRequestRepository;

    @Autowired
    @Qualifier("statusRepositoryImpl")
    private StatusRepository statusRepository;


    public void executeJobAddUserRequestTempUrlAndPassword() {
        LOGGER.info("executeJobAddUserRequestTempUrlAndPassword - - ");
        CommonResponse<List<UserRequest>> listCommonResponse = userRequestRepository.checkUserActiveRequestButNotTempLinkAndPassword();
        if (objectIsNotNull(listCommonResponse) && ListHelper.listIsNotNullAndNotEmpty(listCommonResponse.getObject())) {
            LOGGER.info(listCommonResponse + " success");
            listCommonResponse.getObject().forEach(userRequest -> {
                GenerateRandom generateRandom = new GenerateRandom(GenerateRandom.ENGLISH_LETTERS_AND_NUMBERS);
                userRequest.setTempUrl(generateRandom.generate(99));
                userRequest.setTempPassword(generateRandom.generate(25));
                CommonResponse<Status> statusById = statusRepository.getStatusById(userRequest.getStatus().getStatusId());
                if (ObjectHelper.objectIsNull(statusById)) {
                    LOGGER.error(statusById + " is null");
                    return;
                }
                userRequest.getStatus().setStatusStage(statusById.getObject().getStatusStage() + 1);
                CommonResponse<Status> statusByStage = statusRepository.getStatusByStage(userRequest.getStatus().getStatusStage());
                if (ObjectHelper.objectIsNull(statusByStage)) {
                    LOGGER.error(statusByStage + " is null");
                    return;
                }
                userRequest.setStatus(statusByStage.getObject());
                CommonResponse<Boolean> booleanCommonResponse = userRequestRepository.addUserRequestTempUrlAndPassword(userRequest);
                LOGGER.info(booleanCommonResponse);
            });
        }
        LOGGER.warn(listCommonResponse);
    }

}