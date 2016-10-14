package com.mtsmda.keygen.desktop.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by dminzat on 9/22/2016.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Status {

    @XmlAttribute
    private String statusId;
    @XmlElement
    private String statusValue;
    @XmlElement
    private Integer statusStage;
    @XmlElement
    private List<UserRequest> userRequests;

    public Status() {

    }

    public Status(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public Integer getStatusStage() {
        return statusStage;
    }

    public void setStatusStage(Integer statusStage) {
        this.statusStage = statusStage;
    }

    public List<UserRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(List<UserRequest> userRequests) {
        this.userRequests = userRequests;
    }
}