package com.mtsmda.keygen.desktop.rowMapper;

import com.mtsmda.keygen.desktop.model.Status;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserKey;
import com.mtsmda.keygen.desktop.model.UserRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mtsmda.helper.LocalDateTimeHelper.convertDateToLocalDateTime;
import static com.mtsmda.keygen.desktop.repository.UserRequestRepository.*;

/**
 * Created by dminzat on 9/20/2016.
 */
public class UserRequestRowMapper implements RowMapper<UserRequest> {

    private boolean loadUser;
    private boolean loadStatus;
    private boolean loadUserKey;

    public UserRequestRowMapper() {

    }

    public UserRequestRowMapper(boolean loadUser, boolean loadStatus, boolean loadUserKey) {
        this.loadUser = loadUser;
        this.loadStatus = loadStatus;
        this.loadUserKey = loadUserKey;
    }

    @Override
    public UserRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRequest userRequest = new UserRequest();

        try {
            userRequest.setUserRequestId(rs.getInt(T_USER_REQUESTS_F_USER_REQUEST_ID));
        } catch (Exception e) {
            userRequest.setUserRequestId(null);
        }

        try {
            if (this.loadUser) {
                userRequest.setUser(new UserRowMapper().mapRow(rs, rowNum));
            } else {
                userRequest.setUser(new User(rs.getInt(T_USER_REQUESTS_F_USER_REQUEST_ID)));
            }
        } catch (Exception e) {
            userRequest.setUser(null);
        }

        try {
            userRequest.setRequestDateTime(convertDateToLocalDateTime(rs.getTimestamp(T_USER_REQUESTS_F_REQUEST_DATE_TIME)));
        } catch (Exception e) {
            userRequest.setRequestDateTime(null);
        }

        try {
            userRequest.setRequestIsActive(rs.getBoolean(T_USER_REQUESTS_F_REQUEST_IS_ACTIVE));
        } catch (Exception e) {
            userRequest.setRequestIsActive(null);
        }

        try {
            userRequest.setTempUrl(rs.getString(T_USER_REQUESTS_F_TEMP_URL));
        } catch (Exception e) {
            userRequest.setTempUrl(null);
        }

        try {
            userRequest.setTempPassword(rs.getString(T_USER_REQUESTS_F_TEMP_PASSWORD));
        } catch (Exception e) {
            userRequest.setTempPassword(null);
        }

        try {
            if (this.loadStatus) {
                userRequest.setStatus(new StatusRowMapper().mapRow(rs, rowNum));
            } else {
                userRequest.setStatus(new Status(rs.getString(T_USER_REQUESTS_F_REQUEST_STATUS)));
            }
        } catch (Exception e) {
            userRequest.setStatus(null);
        }

        try {
            if(this.loadUserKey){
                userRequest.setUserKey(new UserKeyRowMapper().mapRow(rs, rowNum));
            }
        } catch (Exception e) {
            userRequest.setUserKey(null);
        }

        return userRequest;
    }

}