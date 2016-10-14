package com.mtsmda.keygen.desktop.model;

import com.mtsmda.keygen.desktop.converter.IConverter;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by dminzat on 9/13/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User{

    @XmlAttribute
    private Integer userId;

    @XmlElement
    private String userEmail;

    @XmlElement
    private LocalDateTime userCreatedUser;

    @XmlElement
    private Boolean enabled;

    @XmlElement
    private String userName;

    @XmlElement
    private String userPassword;

    @XmlElement
    private List<UserRequest> userRequests;

    public User() {

    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String userEmail, LocalDateTime userCreatedUser, Boolean enabled) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userCreatedUser = userCreatedUser;
        this.enabled = enabled;
    }

    public User(String userEmail, LocalDateTime userCreatedUser, String userName, String userPassword) {
        this.userEmail = userEmail;
        this.userCreatedUser = userCreatedUser;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(String userEmail, LocalDateTime userCreatedUser, Boolean enabled, String userName, String userPassword) {
        this.userEmail = userEmail;
        this.userCreatedUser = userCreatedUser;
        this.enabled = enabled;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getUserCreatedUser() {
        return userCreatedUser;
    }

    public void setUserCreatedUser(LocalDateTime userCreatedUser) {
        this.userCreatedUser = userCreatedUser;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<UserRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(List<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userEmail='" + userEmail + '\'' +
                ", userCreatedUser=" + userCreatedUser +
                ", enabled=" + enabled +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}