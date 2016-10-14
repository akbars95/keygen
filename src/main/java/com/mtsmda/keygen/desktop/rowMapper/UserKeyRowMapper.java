package com.mtsmda.keygen.desktop.rowMapper;

import com.mtsmda.keygen.desktop.model.UserKey;
import com.mtsmda.keygen.desktop.model.UserRequest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mtsmda.helper.LocalDateTimeHelper.convertDateToLocalDateTime;
import static com.mtsmda.keygen.desktop.repository.UserKeyRepository.*;

/**
 * Created by dminzat on 9/22/2016.
 */
public class UserKeyRowMapper implements RowMapper<UserKey> {

    private boolean loadUserRequest;

    public UserKeyRowMapper() {

    }

    public UserKeyRowMapper(boolean loadUserRequest) {
        this.loadUserRequest = loadUserRequest;
    }

    @Override
    public UserKey mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserKey userKey = new UserKey();

        try {
            if (this.loadUserRequest) {
                userKey.setUserRequest(new UserRequestRowMapper().mapRow(rs, rowNum));
            } else {
                userKey.setUserRequest(new UserRequest(rs.getInt(T_USER_KEYS_F_USER_REQUEST_ID)));
            }
        } catch (Exception e) {
            userKey.setUserRequest(null);
        }

        try {
            userKey.setGeneratedKey(rs.getString(T_USER_KEYS_F_GENERATED_KEY));
        } catch (Exception e) {
            userKey.setGeneratedKey(null);
        }

        try {
            userKey.setGeneratedLocalDateTime(convertDateToLocalDateTime(rs.getTimestamp(T_USER_KEYS_F_GENERATED_DATETIME)));
        } catch (Exception e) {
            userKey.setGeneratedLocalDateTime(null);
        }

        return userKey;
    }

}