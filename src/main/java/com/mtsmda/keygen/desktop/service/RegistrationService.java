package com.mtsmda.keygen.desktop.service;

import com.mtsmda.keygen.desktop.object.request.EmailRegisterReqObj;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;

/**
 * Created by MTSMDA on 16.09.2016.
 */
public interface RegistrationService {

    CommonResponse<Boolean> registerUser(EmailRegisterReqObj emailRegisterReqObj);

}