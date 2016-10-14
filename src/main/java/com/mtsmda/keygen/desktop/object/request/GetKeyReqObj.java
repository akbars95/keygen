package com.mtsmda.keygen.desktop.object.request;

import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.keygen.desktop.converter.IConverter;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserKey;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.validation.structure.sequence.FourthOrder;

import javax.validation.constraints.NotNull;

/**
 * Created by dminzat on 9/23/2016.
 */
public class GetKeyReqObj extends CommonReqObj implements IConverter<UserRequest> {

    @NotNull(groups = FourthOrder.class)
    private String tempPathURL;

    public String getTempPathURL() {
        return tempPathURL;
    }

    public void setTempPathURL(String tempPathURL) {
        this.tempPathURL = tempPathURL;
    }

    @Override
    public UserRequest convert() {
        return new UserRequest(new User(this.getUserEmail(), null, this.getUserName(), null), this.tempPathURL, this.getUserPassword(), new UserKey(null, null, LocalDateTimeHelper.getLocalDateTime(this.getCreatedDate())));
    }

}