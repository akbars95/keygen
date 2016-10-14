package com.mtsmda.keygen.desktop.object.request;

import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.keygen.desktop.converter.IConverter;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserRequest;

/**
 * Created by dminzat on 9/20/2016.
 */
public class RequestToKeygenReqObj extends CommonReqObj implements IConverter<UserRequest> {

    @Override
    public UserRequest convert() {
        return new UserRequest(new User(this.getUserEmail(), null, this.getUserName(), this.getUserPassword()), LocalDateTimeHelper.getLocalDateTime(getCreatedDate()));
    }
}