package com.mtsmda.keygen.desktop.service;

import com.mtsmda.keygen.desktop.model.UserKey;

/**
 * Created by dminzat on 9/26/2016.
 */
public interface GenerateKeyService {

    UserKey generateKey();
    UserKey generateKey(int countDays);

}