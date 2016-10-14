package com.mtsmda.keygen.desktop.rowMapper;

import com.mtsmda.keygen.desktop.model.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mtsmda.keygen.desktop.repository.StatusRepository.*;

/**
 * Created by dminzat on 9/22/2016.
 */
public class StatusRowMapper implements RowMapper<Status> {

    @Override
    public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
        Status status = new Status();

        try {
            status.setStatusId(rs.getString(T_STATUSES_F_STATUS_ID));
        } catch (Exception e) {
            status.setStatusId(null);
        }

        try {
            status.setStatusValue(rs.getString(T_STATUSES_F_STATUS_VALUE));
        } catch (Exception e) {
            status.setStatusValue(null);
        }

        try {
            status.setStatusStage(rs.getInt(T_STATUSES_F_STATUS_STAGE));
        } catch (Exception e) {
            status.setStatusStage(null);
        }

        return status;
    }

}