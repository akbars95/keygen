package com.mtsmda.keygen.desktop.controller.rest;

import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.object.request.EmailRegisterReqObj;
import com.mtsmda.keygen.desktop.object.request.GetKeyReqObj;
import com.mtsmda.keygen.desktop.object.request.RequestToKeygenReqObj;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.service.KeygenService;
import com.mtsmda.keygen.desktop.service.RegistrationService;
import com.mtsmda.keygen.desktop.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MTSMDA on 13.09.2016.
 */
@RestController
@RequestMapping(value = "/api/desktop", method = RequestMethod.GET)
public class KeygenRestController {

    @Autowired
    @Qualifier("registrationServiceImpl")
    private RegistrationService registrationService;

    @Autowired
    @Qualifier("keygenServiceImpl")
    private KeygenService keygenService;

    @RequestMapping(value = "/getkey", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public CommonResponse<String> registerEmail(@RequestBody GetKeyReqObj getKeyReqObj) {
        System.out.println(getKeyReqObj.getUserEmail());
        CommonResponse<String> booleanCommonResponse = keygenService.generateKey(getKeyReqObj);
        if (ObjectHelper.objectIsNotNull(booleanCommonResponse) && StringUtils.isNotBlank(booleanCommonResponse.getObject())) {
            return booleanCommonResponse;
        }
        return new CommonResponse<>(null, CommonResponse.CONTROLLER_SUCCESS, null);
    }

}