package com.mtsmda.keygen.desktop.object.request;

import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.keygen.desktop.converter.IConverter;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.pattern.Patterns;
import com.mtsmda.validation.structure.constraint.CheckLocalDateTime;
import com.mtsmda.validation.structure.constraint.DateEnum;
import com.mtsmda.validation.structure.constraint.PasswordEquals;
import com.mtsmda.validation.structure.sequence.FirstOrder;
import com.mtsmda.validation.structure.sequence.FourthOrder;
import com.mtsmda.validation.structure.sequence.SecondOrder;
import com.mtsmda.validation.structure.sequence.ThirdOrder;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Created by MTSMDA on 13.09.2016.
 */
@PasswordEquals(className = EmailRegisterReqObj.class, properties = {"userPassword", "confirmUserPassword"}, groups = FourthOrder.class, message = "Password and confirm password is not equals")
public class EmailRegisterReqObj extends CommonReqObj implements IConverter<User> {

    @NotNull(groups = FirstOrder.class, message = "User confirm password isn't be null or empty")
    @Size(min = 5, max = 75, groups = SecondOrder.class, message = "User confirm password size should be min 5 and max 75 chars")
    @Pattern(regexp = Patterns.PASSWORD, groups = ThirdOrder.class, message = "User confirm password should contains: 1 or more numbers and 1 or more small english letter and 1 or more big english letter and 1 or more any these chars: @#$%-.,")
    private String confirmUserPassword;

    public String getConfirmUserPassword() {
        return confirmUserPassword;
    }

    public void setConfirmUserPassword(String confirmUserPassword) {
        this.confirmUserPassword = confirmUserPassword;
    }

    @Override
    public User convert() {
        return new User(getUserEmail(), LocalDateTimeHelper.getLocalDateTime(getCreatedDate()), getUserName(), getUserPassword());
    }

}