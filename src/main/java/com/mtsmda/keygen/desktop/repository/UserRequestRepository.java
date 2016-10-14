package com.mtsmda.keygen.desktop.repository;

import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;

import java.util.List;

/**
 * Created by MTSMDA on 14.09.2016.
 */
public interface UserRequestRepository {

    String T_USER_REQUESTS = "user_requests";
    String T_USER_REQUESTS_F_USER_REQUEST_ID = "user_request_id";
    String T_USER_REQUESTS_F_USER_ID = "user_id";
    String T_USER_REQUESTS_F_REQUEST_DATE_TIME = "request_date_time";
    String T_USER_REQUESTS_F_REQUEST_IS_ACTIVE = "request_is_active";
    String T_USER_REQUESTS_F_TEMP_URL = "temp_url";
    String T_USER_REQUESTS_F_TEMP_PASSWORD = "temp_password";
    String T_USER_REQUESTS_F_REQUEST_STATUS = "request_status";

    public CommonResponse<Boolean> insertUserRequest(UserRequest userRequest);
    public CommonResponse<Boolean> addUserRequestTempUrlAndPassword(UserRequest userRequest);
    public CommonResponse<UserRequest> checkUserActiveRequest(User user);
    public CommonResponse<List<UserRequest>> checkUserActiveRequestButNotTempLinkAndPassword();
    public CommonResponse<UserRequest> getUserRequestByUserId(User user);
    public CommonResponse<UserRequest> getUserRequestByTempPathUrl(String path);
    public CommonResponse<List<UserRequest>> getUserRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword();
    public CommonResponse<Boolean> changeCurrentStatus(UserRequest userRequest);

}