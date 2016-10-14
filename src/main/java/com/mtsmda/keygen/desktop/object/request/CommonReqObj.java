package com.mtsmda.keygen.desktop.object.request;

import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.keygen.desktop.converter.IConverter;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.pattern.Patterns;
import com.mtsmda.validation.structure.constraint.CheckLocalDateTime;
import com.mtsmda.validation.structure.constraint.DateEnum;
import com.mtsmda.validation.structure.constraint.IsNotNull;
import com.mtsmda.validation.structure.constraint.PasswordEquals;
import com.mtsmda.validation.structure.sequence.*;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * Created by MTSMDA on 13.09.2016.
 */
public class CommonReqObj {

    @NotNull(groups = FirstOrder.class, message = "User email address isn't be null or empty")
    @Size(min = 5, max = 128, groups = SecondOrder.class, message = "User email address size should be min 5 and max 128 chars")
    @Email(groups = {ThirdOrder.class, FifthOrder.class}, message = "This email address not right!")
    @IsNotNull(min = 5, max = 128, groups = FourthOrder.class, message = "if user email address isn't be null or empty, then should be User email address size should be min 5 and max 128 chars")
    private String userEmail;

    @NotNull(groups = {FirstOrder.class, FourthOrder.class}, message = "created date should not be null or empty")
    @CheckLocalDateTime(beginTime = true, dateType = DateEnum.LOCAL_DATE_TIME, groups = {SecondOrder.class, FifthOrder.class}, message = "not right created date and time")
    private String createdDate;

    @NotNull(groups = FirstOrder.class, message = "User name isn't be null or empty")
    @Size(min = 5, max = 50, groups = SecondOrder.class, message = "User name size should be min 5 and max 50 chars")
    @Pattern(regexp = Patterns.USERNAME, groups = {ThirdOrder.class, FifthOrder.class}, message = "Username should contains any big or small english symbol or any number and dot(.) and hyphen(-)")
    @IsNotNull(min = 5, max = 50, groups = FourthOrder.class, message = "if user name isn't be null or empty, then should be User name size should be min 5 and max 50 chars")
    private String userName;

    @NotNull(groups = {FirstOrder.class, FourthOrder.class}, message = "User password isn't be null or empty")
    @Size(min = 5, max = 50, groups = {SecondOrder.class, FifthOrder.class}, message = "User password size should be min 5 and max 50 chars")
    @Pattern(regexp = Patterns.PASSWORD, groups = {ThirdOrder.class}, message = "User password should contains: 1 or more numbers and 1 or more small english letter and 1 or more big english letter and 1 or more any these chars: @#$%-.,")
    private String userPassword;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

}