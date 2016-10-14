package com.mtsmda.keygen.desktop.repository;

import com.mtsmda.keygen.desktop.model.UserKey;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;

/**
 * Created by MTSMDA on 14.09.2016.
 */
public interface UserKeyRepository {

    String T_USER_KEYS = "user_keys";
    String T_USER_KEYS_F_USER_REQUEST_ID = "user_request_id";
    String T_USER_KEYS_F_GENERATED_KEY = "generated_key";
    String T_USER_KEYS_F_GENERATED_DATETIME = "generated_datetime";

    CommonResponse<Boolean> insertUserKey(UserKey userKey);
    CommonResponse<String> getUserKey(UserRequest userRequest);
    CommonResponse<Integer> checkAlreadyHadGeneratedKeyAndIsActive(String genKey);

}