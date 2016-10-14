package com.mtsmda.keygen.desktop.service;

import com.mtsmda.keygen.desktop.object.request.GetKeyReqObj;
import com.mtsmda.keygen.desktop.object.request.RequestToKeygenReqObj;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;

/**
 * Created by dminzat on 9/20/2016.
 */
public interface KeygenService {

    public CommonResponse<Boolean> requestToKeygen(RequestToKeygenReqObj requestToKeygenReqObj);

    public CommonResponse<String> generateKey(GetKeyReqObj getKeyReqObj);

}