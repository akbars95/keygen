package com.mtsmda.keygen.desktop.object.response;

/**
 * Created by MTSMDA on 31.08.2016.
 */
public class CommonResponse<T> {

    public static final Integer SUCCESS = 0;
    public static final Integer ERROR = -1;

    public static final Integer REPOSITORY_SUCCESS = 100;
    public static final Integer REPOSITORY_INSERT = 101;
    public static final Integer REPOSITORY_UPDATE = 102;
    public static final Integer REPOSITORY_DELETE = 103;
    public static final Integer REPOSITORY_GET = 104;
    public static final Integer REPOSITORY_GET_ALL = 105;


    public static final Integer SERVICE_SUCCESS = 200;
    public static final Integer SERVICE_ERROR = 201;

    public static final Integer VALIDATION_SUCCESS = 300;
    public static final Integer VALIDATION_ERROR = 301;

    public static final Integer TRANSFORMATION_SUCCESS = 400;
    public static final Integer TRANSFORMATION_ERROR = 401;

    public static final Integer CONTROLLER_SUCCESS = 500;
    public static final Integer CONTROLLER_ERROR = 501;

    public static final Integer EMAIL_SERVICE_ERROR = 600;
    public static final Integer EMAIL_SERVICE_SUCCESS = 601;

    private T object;
    private Integer code;
    private String messageErrorDescription;

    public CommonResponse() {

    }

    public CommonResponse(Integer code) {
        this.code = code;
    }

    public CommonResponse(Integer code, String messageErrorDescription) {
        this.code = code;
        this.messageErrorDescription = messageErrorDescription;
    }

    public CommonResponse(T object, Integer code, String messageErrorDescription) {
        this.object = object;
        this.code = code;
        this.messageErrorDescription = messageErrorDescription;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessageErrorDescription() {
        return messageErrorDescription;
    }

    public void setMessageErrorDescription(String messageErrorDescription) {
        this.messageErrorDescription = messageErrorDescription;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "object=" + object +
                ", code=" + code +
                ", messageErrorDescription='" + messageErrorDescription + '\'' +
                '}';
    }
}