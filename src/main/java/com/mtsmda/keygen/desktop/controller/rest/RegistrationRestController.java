package com.mtsmda.keygen.desktop.controller.rest;

import com.mtsmda.keygen.desktop.object.request.EmailRegisterReqObj;
import com.mtsmda.keygen.desktop.object.request.RequestToKeygenReqObj;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.service.KeygenService;
import com.mtsmda.keygen.desktop.service.RegistrationService;
import com.mtsmda.keygen.desktop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MTSMDA on 13.09.2016.
 */
@RestController
@RequestMapping(value = "/api/desktop", method = RequestMethod.GET)
public class RegistrationRestController {

    @Autowired
    @Qualifier("keygenServiceImpl")
    private KeygenService keygenService;

    @Autowired
    @Qualifier("registrationServiceImpl")
    private RegistrationService registrationService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResponse<Boolean> registerEmail(@RequestBody EmailRegisterReqObj emailRegisterReqObj) {
        System.out.println(emailRegisterReqObj.getUserEmail());
        return registrationService.registerUser(emailRegisterReqObj);
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public CommonResponse<Boolean> registerEmail(@RequestBody RequestToKeygenReqObj requestToKeygenReqObj){
        System.out.println(requestToKeygenReqObj);
        return keygenService.requestToKeygen(requestToKeygenReqObj);
    }

}