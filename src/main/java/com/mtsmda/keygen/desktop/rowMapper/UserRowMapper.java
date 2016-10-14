package com.mtsmda.keygen.desktop.rowMapper;

import com.mtsmda.keygen.desktop.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.mtsmda.keygen.desktop.repository.UserRepository.*;
import static com.mtsmda.helper.LocalDateTimeHelper.*;

/**
 * Created by dminzat on 9/20/2016.
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        try {
            user.setUserId(rs.getInt(T_USERS_F_USER_ID));
        } catch (Exception e) {
            user.setUserId(null);
        }

        try {
            user.setUserEmail(rs.getString(T_USERS_F_USER_EMAIL));
        } catch (Exception e) {
            user.setUserEmail(null);
        }

        try {
            user.setUserCreatedUser(convertDateToLocalDateTime(rs.getTimestamp(T_USERS_F_USER_CREATED_DATE_TIME)));
        } catch (Exception e) {
            user.setUserCreatedUser(null);
        }

        try {
            user.setEnabled(rs.getBoolean(T_USERS_F_USER_ENABLED));
        } catch (Exception e) {
            user.setEnabled(null);
        }

        try {
            user.setUserName(rs.getString(T_USERS_F_USER_USERNAME));
        } catch (Exception e) {
            user.setUserName(null);
        }

        try {
            user.setUserPassword(rs.getString(T_USERS_F_USER_USERNAME));
        } catch (Exception e) {
            user.setUserPassword(null);
        }

        try {
            user.setUserRequests(new ArrayList<>());
            user.getUserRequests().add(new UserRequestRowMapper().mapRow(rs, rowNum));
        } catch (Exception e) {
            user.setUserRequests(null);
        }

        return user;
    }
}