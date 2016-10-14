package com.mtsmda.keygen.desktop.service;

import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.Status;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.request.CommonReqObj;
import com.mtsmda.keygen.desktop.object.request.EmailRegisterReqObj;
import com.mtsmda.keygen.desktop.object.request.GetKeyReqObj;
import com.mtsmda.keygen.desktop.object.request.RequestToKeygenReqObj;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.repository.StatusRepository;
import com.mtsmda.keygen.desktop.repository.UserKeyRepository;
import com.mtsmda.keygen.desktop.repository.UserRepository;
import com.mtsmda.keygen.desktop.repository.UserRequestRepository;
import com.mtsmda.keygen.desktop.validation.sequence.RequestToKeygenOrderGroupSequence;
import com.mtsmda.validation.structure.StructureValidator;
import com.mtsmda.validation.structure.sequence.OrderGroupSequence;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dminzat on 9/20/2016.
 */
@Service("keygenServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class KeygenServiceImpl implements KeygenService {

    private static final Logger LOGGER = Logger.getLogger(KeygenServiceImpl.class);
    private StructureValidator<RequestToKeygenReqObj> structureValidator = new StructureValidator<>();
    private StructureValidator<GetKeyReqObj> structureValidatorGetKeyReqObj = new StructureValidator<>();

    @Autowired
    @Qualifier("userRepositoryImpl")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("userRequestRepositoryImpl")
    private UserRequestRepository userRequestRepository;

    @Autowired
    @Qualifier("statusRepositoryImpl")
    private StatusRepository statusRepository;

    @Autowired
    @Qualifier("userKeyRepositoryImpl")
    private UserKeyRepository userKeyRepository;

    @Override
    public CommonResponse<Boolean> requestToKeygen(RequestToKeygenReqObj requestToKeygenReqObj) {
        LOGGER.info(requestToKeygenReqObj);
        checkEmailAndUsername(requestToKeygenReqObj);
        StructureValidator<RequestToKeygenReqObj>.StructureValidationResult validate = structureValidator.validate(requestToKeygenReqObj, RequestToKeygenOrderGroupSequence.class);
        LOGGER.info("after validation - is success " + validate.getSuccessValidation());
        if (!validate.getSuccessValidation()) {
            LOGGER.warn("validation error " + (!validate.getSuccessValidation()));
            LOGGER.warn(validate.getStringMessageForLogger());
            return new CommonResponse<>(false, CommonResponse.VALIDATION_ERROR, validate.getStringMessageForLogger());
        }
        UserRequest convertUserRequest = requestToKeygenReqObj.convert();
        if (ObjectHelper.objectIsNull(convertUserRequest)) {
            LOGGER.warn("convert process error!");
            return new CommonResponse<>(false, CommonResponse.TRANSFORMATION_ERROR, "error convert process");
        }

        CommonResponse<User> userByUserEmailOrUsernameAndUserPassword = userRepository.getUserByUserEmailOrUsernameAndUserPassword(convertUserRequest.getUser());
        LOGGER.info(userByUserEmailOrUsernameAndUserPassword);
        if (ObjectHelper.objectIsNull(userByUserEmailOrUsernameAndUserPassword.getObject())) {
            LOGGER.info("Cannot find this user - " + convertUserRequest.getUser().getUserName());
            return new CommonResponse<>(false, CommonResponse.SERVICE_ERROR, userByUserEmailOrUsernameAndUserPassword.getMessageErrorDescription());
        }
        convertUserRequest.setUser(userByUserEmailOrUsernameAndUserPassword.getObject());

        CommonResponse<UserRequest> userRequestCommonResponse = userRequestRepository.checkUserActiveRequest(convertUserRequest.getUser());

        if (ObjectHelper.objectIsNotNull(userRequestCommonResponse.getObject())) {
            String message = "You are already active key!";
            LOGGER.warn(message);
            return new CommonResponse<>(true, CommonResponse.SERVICE_SUCCESS, message);
        }
        convertUserRequest.setRequestIsActive(true);
        CommonResponse<Status> statusByStage = statusRepository.getStatusByStage(1);
        if (ObjectHelper.objectIsNull(statusByStage)) {
            return new CommonResponse<>(false, CommonResponse.SERVICE_ERROR, statusByStage.getMessageErrorDescription());
        }
        convertUserRequest.setStatus(statusByStage.getObject());
        CommonResponse<Boolean> booleanCommonResponse = userRequestRepository.insertUserRequest(convertUserRequest);

        LOGGER.info("convert request object to user object - " + convertUserRequest);
        return booleanCommonResponse;
    }

    @Override
    public CommonResponse<String> generateKey(GetKeyReqObj getKeyReqObj) {
        LOGGER.info(getKeyReqObj);
        checkEmailAndUsername(getKeyReqObj);
        StructureValidator<GetKeyReqObj>.StructureValidationResult validate = structureValidatorGetKeyReqObj.validate(getKeyReqObj, RequestToKeygenOrderGroupSequence.class);
        LOGGER.info("after validation - is success " + validate.getSuccessValidation());
        if (!validate.getSuccessValidation()) {
            LOGGER.warn("validation error " + (!validate.getSuccessValidation()));
            LOGGER.warn(validate.getStringMessageForLogger());
            return new CommonResponse<>(null, CommonResponse.VALIDATION_ERROR, validate.getStringMessageForLogger());
        }
        LOGGER.info(validate);
        UserRequest convertUserRequest = getKeyReqObj.convert();
        LOGGER.info("convert - " + convertUserRequest);
        CommonResponse<String> userKey = userKeyRepository.getUserKey(convertUserRequest);
        if (ObjectHelper.objectIsNull(userKey) || StringUtils.isBlank(userKey.getObject())) {
            return new CommonResponse<>(null, CommonResponse.SERVICE_ERROR, "return user generated key is null");
        }

        CommonResponse<Integer> integerCommonResponse = userKeyRepository.checkAlreadyHadGeneratedKeyAndIsActive(userKey.getObject());
        if (ObjectHelper.objectIsNotNull(integerCommonResponse.getObject())) {
            return userKey;
        }

        CommonResponse<UserRequest> userRequestByTempPathUrl = userRequestRepository.getUserRequestByTempPathUrl(convertUserRequest.getTempUrl());
        if (ObjectHelper.objectIsNull(userRequestByTempPathUrl.getObject())) {
            return new CommonResponse<>(null, CommonResponse.SERVICE_ERROR, "Cannot get user request!");
        }
        convertUserRequest.setUserRequestId(userRequestByTempPathUrl.getObject().getUserRequestId());
        CommonResponse<Boolean> booleanCommonResponse = userRequestRepository.changeCurrentStatus(convertUserRequest);
        if (!booleanCommonResponse.getObject()) {
            return new CommonResponse<>(null, CommonResponse.SERVICE_ERROR, "cannot change status!");
        }
        return userKey;
    }

    private <T extends CommonReqObj> void checkEmailAndUsername(T t) {
        if (StringUtils.isBlank(t.getUserEmail())) {
            t.setUserEmail(null);
        }
        if (StringUtils.isBlank(t.getUserName())) {
            t.setUserName(null);
        }
    }

}