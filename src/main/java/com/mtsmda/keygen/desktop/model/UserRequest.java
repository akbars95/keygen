package com.mtsmda.keygen.desktop.model;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;

/**
 * Created by dminzat on 9/20/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserRequest {

    @XmlAttribute
    private Integer userRequestId;

    @XmlElement
    private User user;

    @XmlElement
    private LocalDateTime requestDateTime;

    @XmlElement
    private Boolean requestIsActive;

    @XmlElement
    private String tempUrl;

    @XmlElement
    private String tempPassword;

    @XmlElement
    private UserKey userKey;

    @XmlElement
    private Status status;

    public UserRequest() {

    }

    public UserRequest(Integer userRequestId) {
        this.userRequestId = userRequestId;
    }

    public UserRequest(User user, LocalDateTime requestDateTime) {
        this.user = user;
        this.requestDateTime = requestDateTime;
    }

    public UserRequest(User user, String tempUrl, String tempPassword, UserKey userKey) {
        this.user = user;
        this.tempUrl = tempUrl;
        this.tempPassword = tempPassword;
        this.userKey = userKey;
    }

    public Integer getUserRequestId() {
        return userRequestId;
    }

    public void setUserRequestId(Integer userRequestId) {
        this.userRequestId = userRequestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(LocalDateTime requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public Boolean getRequestIsActive() {
        return requestIsActive;
    }

    public void setRequestIsActive(Boolean requestIsActive) {
        this.requestIsActive = requestIsActive;
    }

    public String getTempUrl() {
        return tempUrl;
    }

    public void setTempUrl(String tempUrl) {
        this.tempUrl = tempUrl;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public UserKey getUserKey() {
        return userKey;
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}