package com.mtsmda.keygen.desktop.repository;

import com.mtsmda.helper.ExceptionMessageHelper;
import com.mtsmda.helper.ListHelper;
import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.Status;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.rowMapper.StatusRowMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.mtsmda.helper.ExceptionMessageHelper.exceptionDescription;
import static com.mtsmda.helper.QueryCreatorHelper.selectAll;
import static com.mtsmda.helper.QueryCreatorHelper.selectById;
import static com.mtsmda.keygen.desktop.object.response.CommonResponse.*;

/**
 * Created by dminzat on 9/22/2016.
 */
@Repository("statusRepositoryImpl")
public class StatusRepositoryImpl extends RepositoryParent implements StatusRepository {

    private static final Logger LOGGER = Logger.getLogger(StatusRepositoryImpl.class);

    @Override
    public CommonResponse<Status> getStatusByName(String name) {
        return getByOneWhereClause(name, "status name", T_STATUSES_F_STATUS_VALUE);
    }

    @Override
    public CommonResponse<Status> getStatusById(String id) {
        return getByOneWhereClause(id, "status id", T_STATUSES_F_STATUS_ID);
    }

    @Override
    public CommonResponse<Status> getStatusByStage(Integer stage) {
        return getByOneWhereClause(stage, "status stage name", T_STATUSES_F_STATUS_STAGE);
    }

    @Override
    public CommonResponse<List<Status>> getAllStatuses() {
        List<Status> statuses = null;
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            setQuery(selectAll(T_STATUSES));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            statuses = namedParameterJdbcTemplate.query(getQuery(), params, new StatusRowMapper());
            if (ListHelper.listIsNullOrEmpty(statuses)) {
                setMessageForLogger("return statuses is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET_ALL, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(ExceptionMessageHelper.exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET_ALL, getMessageForLogger());
        }
        return new CommonResponse<>(statuses, REPOSITORY_SUCCESS, null);
    }

    private <T> CommonResponse<Status> getByOneWhereClause(T object, String isNullMessage, String clause) {
        Status status = null;
        try {
            if (ObjectHelper.objectIsNull(object)) {
                setMessageForLogger(isNullMessage + " " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
            setQuery(selectById(T_STATUSES, clause));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(clause, object);
            status = namedParameterJdbcTemplate.queryForObject(getQuery(), params, new StatusRowMapper());
            if (ObjectHelper.objectIsNull(status)) {
                setMessageForLogger("return " + isNullMessage + " is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(status, REPOSITORY_SUCCESS, null);
    }
}
