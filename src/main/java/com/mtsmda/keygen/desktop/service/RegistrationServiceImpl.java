package com.mtsmda.keygen.desktop.service;

import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.object.request.EmailRegisterReqObj;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.repository.UserRepository;
import com.mtsmda.validation.structure.StructureValidator;
import com.mtsmda.validation.structure.sequence.OrderGroupSequence;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by MTSMDA on 16.09.2016.
 */
@Service("registrationServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class RegistrationServiceImpl implements RegistrationService{

    private static final Logger LOGGER = Logger.getLogger(RegistrationServiceImpl.class);
    private StructureValidator<EmailRegisterReqObj> structureValidator = new StructureValidator<>();

    @Autowired
    @Qualifier("userRepositoryImpl")
    private UserRepository userRepository;

    @Override
    public CommonResponse<Boolean> registerUser(EmailRegisterReqObj emailRegisterReqObj) {
        LOGGER.info(emailRegisterReqObj);
        StructureValidator<EmailRegisterReqObj>.StructureValidationResult validate = structureValidator.validate(emailRegisterReqObj, OrderGroupSequence.class);
        LOGGER.info("after validation - is success " + validate.getSuccessValidation());
        if(!validate.getSuccessValidation()){
            LOGGER.warn("validation error " + (!validate.getSuccessValidation()));
            LOGGER.warn(validate.getStringMessageForLogger());
            return new CommonResponse<>(false, CommonResponse.VALIDATION_ERROR, validate.getStringMessageForLogger());
        }
        User userConvert = emailRegisterReqObj.convert();
        if(ObjectHelper.objectIsNull(userConvert)){
            LOGGER.warn("convert process error!");
            return new CommonResponse<>(false, CommonResponse.TRANSFORMATION_ERROR, "error convert process");
        }
        LOGGER.info("convert request object to user object - " + userConvert);
        CommonResponse<Boolean> booleanCommonResponseInsertUser = userRepository.insertUser(userConvert);

        return booleanCommonResponseInsertUser;
    }
}