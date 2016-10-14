package com.mtsmda.keygen.desktop.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

/**
 * Created by dminzat on 9/20/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserKey {

    @XmlElement
    private UserRequest userRequest;

    @XmlElement
    private String generatedKey;

    @XmlElement
    private LocalDateTime generatedLocalDateTime;

    public UserKey() {

    }

    public UserKey(UserRequest userRequest, String generatedKey, LocalDateTime generatedLocalDateTime) {
        this.userRequest = userRequest;
        this.generatedKey = generatedKey;
        this.generatedLocalDateTime = generatedLocalDateTime;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public String getGeneratedKey() {
        return generatedKey;
    }

    public void setGeneratedKey(String generatedKey) {
        this.generatedKey = generatedKey;
    }

    public LocalDateTime getGeneratedLocalDateTime() {
        return generatedLocalDateTime;
    }

    public void setGeneratedLocalDateTime(LocalDateTime generatedLocalDateTime) {
        this.generatedLocalDateTime = generatedLocalDateTime;
    }
}