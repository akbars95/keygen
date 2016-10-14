package com.mtsmda.keygen.desktop.repository;

import com.mtsmda.helper.ExceptionMessageHelper;
import com.mtsmda.helper.ListHelper;
import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.Status;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.rowMapper.UserRequestRowMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.mtsmda.helper.ExceptionMessageHelper.exceptionDescription;
import static com.mtsmda.helper.ListHelper.getListWithData;
import static com.mtsmda.helper.QueryCreatorHelper.insertGenerate;
import static com.mtsmda.helper.QueryCreatorHelper.selectById;
import static com.mtsmda.helper.QueryCreatorHelper.updateGenerate;
import static com.mtsmda.keygen.desktop.object.response.CommonResponse.*;

/**
 * Created by dminzat on 9/21/2016.
 */
@Repository("userRequestRepositoryImpl")
public class UserRequestRepositoryImpl extends RepositoryParent implements UserRequestRepository {

    private static final Logger LOGGER = Logger.getLogger(UserRequestRepositoryImpl.class);

    @Autowired
    @Qualifier("statusRepositoryImpl")
    private StatusRepository statusRepository;

    @Override
    public CommonResponse<Boolean> insertUserRequest(UserRequest userRequest) {
        try {
            if (ObjectHelper.objectIsNull(userRequest)) {
                setMessageForLogger("userRequest " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
            }
            setQuery(insertGenerate(T_USER_REQUESTS, getListWithData(T_USER_REQUESTS_F_USER_ID, T_USER_REQUESTS_F_REQUEST_DATE_TIME
                    , T_USER_REQUESTS_F_REQUEST_IS_ACTIVE, T_USER_REQUESTS_F_REQUEST_STATUS)));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USER_REQUESTS_F_USER_ID, userRequest.getUser().getUserId());
            params.put(T_USER_REQUESTS_F_REQUEST_DATE_TIME, LocalDateTimeHelper.localDateTimeMySqlFormat(userRequest.getRequestDateTime()));
            params.put(T_USER_REQUESTS_F_REQUEST_IS_ACTIVE, userRequest.getRequestIsActive());
            params.put(T_USER_REQUESTS_F_REQUEST_STATUS, userRequest.getStatus().getStatusId());
            int update = namedParameterJdbcTemplate.update(getQuery(), params);
            if (update <= 0) {
                setMessageForLogger(MESSAGE_CANNOT_INSERT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
        }
        return new CommonResponse<>(true, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<Boolean> addUserRequestTempUrlAndPassword(UserRequest userRequest) {
        try {
            if (ObjectHelper.objectIsNull(userRequest)) {
                setMessageForLogger("userRequest " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_UPDATE, getMessageForLogger());
            }
            /*
            * update user_requests ur set ur.temp_url = :temp_url, ur.temp_password = :temp_password
                    where ur.user_request_id = :user_request_id;
            * */
            setQuery(updateGenerate(T_USER_REQUESTS, ListHelper.getListWithData(T_USER_REQUESTS_F_TEMP_URL, T_USER_REQUESTS_F_TEMP_PASSWORD, T_USER_REQUESTS_F_REQUEST_STATUS),
                    T_USER_REQUESTS_F_USER_ID));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USER_REQUESTS_F_TEMP_URL, userRequest.getTempUrl());
            params.put(T_USER_REQUESTS_F_TEMP_PASSWORD, userRequest.getTempPassword());
            params.put(T_USER_REQUESTS_F_REQUEST_STATUS, userRequest.getStatus().getStatusId());
            params.put(T_USER_REQUESTS_F_USER_ID, userRequest.getUser().getUserId());
            int update = namedParameterJdbcTemplate.update(getQuery(), params);
            if (update <= 0) {
                setMessageForLogger(MESSAGE_CANNOT_INSERT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_UPDATE, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(false, REPOSITORY_UPDATE, getMessageForLogger());
        }
        return new CommonResponse<>(true, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<UserRequest> checkUserActiveRequest(User user) {
        UserRequest responseUserRequest = null;
        try {
            if (ObjectHelper.objectIsNull(user)) {
                setMessageForLogger("user " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
            setQuery("select ur.*\n" +
                    "from user_requests ur\n" +
                    "where ur.user_id = :user_id and ur.request_is_active = 1;");
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USER_REQUESTS_F_USER_ID, user.getUserEmail());
            responseUserRequest = namedParameterJdbcTemplate.queryForObject(getQuery(), params, new UserRequestRowMapper());
            if (ObjectHelper.objectIsNull(responseUserRequest)) {
                setMessageForLogger("return responseUserRequest is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(responseUserRequest, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<List<UserRequest>> checkUserActiveRequestButNotTempLinkAndPassword() {
        List<UserRequest> responseUserRequests = null;
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            setQuery("SELECT ur.*\n" +
                    "FROM user_requests ur\n" +
                    "where (ur.temp_url is null or ur.temp_url = '') and (ur.temp_password is null or ur.temp_password = '') " +
                    "and ur.request_is_active = 1 and ur.request_status = (SELECT s.status_id\n" +
                    "FROM statuses s\n" +
                    "where s.status_stage = 1)");
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            responseUserRequests = namedParameterJdbcTemplate.query(getQuery(), params, new UserRequestRowMapper());
            if (ListHelper.listIsNullOrEmpty(responseUserRequests)) {
                setMessageForLogger("return responseUserRequests is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET_ALL, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(ExceptionMessageHelper.exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET_ALL, getMessageForLogger());
        }
        return new CommonResponse<>(responseUserRequests, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<UserRequest> getUserRequestByUserId(User user) {
        UserRequest responseUserRequest = null;
        try {
            if (ObjectHelper.objectIsNull(user)) {
                setMessageForLogger("user " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
            setQuery(selectById(T_USER_REQUESTS, T_USER_REQUESTS_F_USER_ID));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USER_REQUESTS_F_USER_ID, user.getUserEmail());
            responseUserRequest = namedParameterJdbcTemplate.queryForObject(getQuery(), params, new UserRequestRowMapper());
            if (ObjectHelper.objectIsNull(responseUserRequest)) {
                setMessageForLogger("return responseUserRequest is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(responseUserRequest, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<UserRequest> getUserRequestByTempPathUrl(String path) {
        UserRequest responseUserRequest = null;
        try {
            if (ObjectHelper.objectIsNull(path)) {
                setMessageForLogger("temp path " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
            setQuery("SELECT ur.*\n" +
                    "FROM user_requests ur\n" +
                    "where ur.temp_url = :temp_url and ur.request_is_active = 1 and ur.request_status = any(\n" +
                    "select s.status_id\n" +
                    "from statuses s\n" +
                    "where s.status_stage in(3,4)\n" +
                    ");");
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USER_REQUESTS_F_TEMP_URL, path);
            responseUserRequest = namedParameterJdbcTemplate.queryForObject(getQuery(), params, new UserRequestRowMapper());
            if (ObjectHelper.objectIsNull(responseUserRequest)) {
                setMessageForLogger("return responseUserRequest is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(responseUserRequest, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<List<UserRequest>> getUserRequestByTempPathUrlTempPasswordEmailOrNameUserAndPassword() {
        List<UserRequest> userRequests = null;
        try {
            setQuery("SELECT ur.*, u.*\n" +
                    "FROM user_requests ur inner join users u\n" +
                    "on ur.user_id = u.user_id\n" +
                    "where ur.request_is_active = 1 and ur.temp_url is not null and ur.temp_password is not null\n" +
                    "and ur.request_status = (select s.status_id\n" +
                    "\t\t\t\t\t\tfrom statuses s\n" +
                    "                        where s.status_stage = 2);");
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            userRequests = namedParameterJdbcTemplate.query(getQuery(), params, new UserRequestRowMapper(true, false, false));
            if (ObjectHelper.objectIsNull(userRequests)) {
                setMessageForLogger("return userRequests is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(userRequests, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<Boolean> changeCurrentStatus(UserRequest userRequest) {
        try {
            if (ObjectHelper.objectIsNull(userRequest)) {
                setMessageForLogger("userRequest " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
            }
            setQuery("update user_requests set request_status = \n" +
                    "(select status_id\n" +
                    "from (\n" +
                    "(select s.status_id\n" +
                    "from statuses s\n" +
                    "where status_stage = (\n" +
                    "(SELECT s.status_stage + 1\n" +
                    "FROM user_requests ur inner join statuses s\n" +
                    "on ur.request_status = s.status_id\n" +
                    "where ur.user_request_id = :user_request_id)\n" +
                    ")\n" +
                    ") as u_r_s)\n" +
                    ")\n" +
                    "where user_request_id = :user_request_id;");
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USER_REQUESTS_F_USER_REQUEST_ID, userRequest.getUserRequestId());
            int update = namedParameterJdbcTemplate.update(getQuery(), params);
            if (update <= 0) {
                setMessageForLogger(MESSAGE_CANNOT_INSERT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
        }
        return new CommonResponse<>(true, REPOSITORY_SUCCESS, null);
    }

}