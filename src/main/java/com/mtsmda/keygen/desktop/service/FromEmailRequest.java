package com.mtsmda.keygen.desktop.service;

import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;

/**
 * Created by dminzat on 9/23/2016.
 */
public interface FromEmailRequest {

    public CommonResponse<UserRequest> checkExistsTempUrl(String path);

}