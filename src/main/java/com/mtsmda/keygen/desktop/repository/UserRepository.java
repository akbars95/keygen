package com.mtsmda.keygen.desktop.repository;

import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;

import java.util.List;

/**
 * Created by dminzat on 9/14/2016.
 */
public interface UserRepository {

    String T_USERS = "users";
    String T_USERS_F_USER_ID = "user_id";
    String T_USERS_F_USER_EMAIL = "user_email";
    String T_USERS_F_USER_CREATED_DATE_TIME = "user_created_date_time";
    String T_USERS_F_USER_ENABLED = "user_enabled";
    String T_USERS_F_USER_USERNAME = "user_username";
    String T_USERS_F_USER_PASSWORD = "user_password";

    CommonResponse<Boolean> insertUser(User user);

    CommonResponse<User> getUserByUserEmailOrUsernameAndUserPassword(User user);

    CommonResponse<User> getUserByUserId(Integer userId);

    CommonResponse<List<User>> getAllUsers();

}