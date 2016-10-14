package com.mtsmda.keygen.desktop.service;

import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;

import java.util.List;

/**
 * Created by dminzat on 9/21/2016.
 */
public interface UserService {

    public CommonResponse<List<User>> getAllUsers();

}