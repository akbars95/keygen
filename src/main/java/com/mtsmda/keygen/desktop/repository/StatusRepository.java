package com.mtsmda.keygen.desktop.repository;

import com.mtsmda.keygen.desktop.model.Status;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;

import java.util.List;

/**
 * Created by dminzat on 9/22/2016.
 */
public interface StatusRepository {

    String T_STATUSES = "statuses";
    String T_STATUSES_F_STATUS_ID = "status_id";
    String T_STATUSES_F_STATUS_VALUE = "status_value";
    String T_STATUSES_F_STATUS_STAGE = "status_stage";

    CommonResponse<Status> getStatusByName(String name);

    CommonResponse<Status> getStatusById(String id);

    CommonResponse<Status> getStatusByStage(Integer stage);

    CommonResponse<List<Status>> getAllStatuses();

}