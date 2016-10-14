package com.mtsmda.keygen.desktop.service;

import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by dminzat on 9/23/2016.
 */
@Service("fromEmailRequestImpl")
public class FromEmailRequestImpl implements FromEmailRequest {

    @Autowired
    @Qualifier("userRequestRepositoryImpl")
    private UserRequestRepository userRequestRepository;

    @Override
    public CommonResponse<UserRequest> checkExistsTempUrl(String path) {
        return userRequestRepository.getUserRequestByTempPathUrl(path);
    }
}