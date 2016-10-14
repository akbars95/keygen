package com.mtsmda.keygen.desktop.repository;

import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.model.UserKey;
import com.mtsmda.keygen.desktop.model.UserRequest;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.rowMapper.UserRowMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.mtsmda.helper.ExceptionMessageHelper.exceptionDescription;
import static com.mtsmda.helper.ListHelper.getListWithData;
import static com.mtsmda.helper.QueryCreatorHelper.insertGenerate;
import static com.mtsmda.keygen.desktop.object.response.CommonResponse.*;
import static com.mtsmda.keygen.desktop.repository.UserRepository.*;
import static com.mtsmda.keygen.desktop.repository.UserRequestRepository.*;

/**
 * Created by dminzat on 9/22/2016.
 */
@Repository("userKeyRepositoryImpl")
public class UserKeyRepositoryImpl extends RepositoryParent implements UserKeyRepository {

    private static final Logger LOGGER = Logger.getLogger(UserKeyRepositoryImpl.class);

    @Override
    public CommonResponse<Boolean> insertUserKey(UserKey userKey) {
        try {
            if (ObjectHelper.objectIsNull(userKey) && ObjectHelper.objectIsNull(userKey.getUserRequest())) {
                setMessageForLogger("userKey and userRequest " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
            }
            setQuery(insertGenerate(T_USER_KEYS, getListWithData(T_USER_KEYS_F_USER_REQUEST_ID, T_USER_KEYS_F_GENERATED_KEY
                    , T_USER_KEYS_F_GENERATED_DATETIME)));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USER_KEYS_F_USER_REQUEST_ID, userKey.getUserRequest().getUserRequestId());
            params.put(T_USER_KEYS_F_GENERATED_KEY, userKey.getGeneratedKey());
            params.put(T_USER_KEYS_F_GENERATED_DATETIME, LocalDateTimeHelper.localDateTimeMySqlFormat(userKey.getGeneratedLocalDateTime()));
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
    public CommonResponse<String> getUserKey(UserRequest userRequest) {
        String resultGeneratedKey = null;
        try {
            if (ObjectHelper.objectIsNull(userRequest)) {
                setMessageForLogger("userRequest " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
            setQuery("SELECT uk.generated_key\n" +
                    "FROM user_keys uk inner join user_requests ur\n" +
                    "on uk.user_request_id = ur.user_request_id\n" +
                    "inner join users u \n" +
                    "on ur.user_id = u.user_id\n" +
                    "where (u.user_email = :user_email or u.user_username = :user_username) and ur.temp_url = :temp_url\n" +
                    "and ur.temp_password = :temp_password\n" +
                    "and ur.request_is_active = 1 and (uk.generated_key is not null or uk.generated_key != '')\n" +
                    "and ur.request_status = some(\n" +
                    "\tselect s.status_id\n" +
                    "    from statuses s\n" +
                    "    where s.status_stage in(3, 4)\n" +
                    ");");
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USERS_F_USER_EMAIL, userRequest.getUser().getUserEmail());
            params.put(T_USERS_F_USER_USERNAME, userRequest.getUser().getUserName());
            params.put(T_USER_REQUESTS_F_TEMP_URL, userRequest.getTempUrl());
            params.put(T_USER_REQUESTS_F_TEMP_PASSWORD, userRequest.getTempPassword());
            resultGeneratedKey = namedParameterJdbcTemplate.queryForObject(getQuery(), params, (rs, rowNum) -> {
                return rs.getString("generated_key");
            });
            if (ObjectHelper.objectIsNull(resultGeneratedKey)) {
                setMessageForLogger("return generated key is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(resultGeneratedKey, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<Integer> checkAlreadyHadGeneratedKeyAndIsActive(String genKey) {
        Integer id = null;
        try {
            if (ObjectHelper.objectIsNull(genKey)) {
                setMessageForLogger("genKey " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
            setQuery("SELECT ur.user_request_id\n" +
                    "FROM user_keys uk inner join user_requests ur\n" +
                    "on uk.user_request_id = ur.user_request_id\n" +
                    "where uk.generated_key =:generated_key and ur.request_status = (\n" +
                    "select s.status_id\n" +
                    "from statuses s\n" +
                    "where s.status_stage = 4\n" +
                    ") and ur.request_is_active = 1;");
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USER_KEYS_F_GENERATED_KEY, genKey);
            id = namedParameterJdbcTemplate.queryForObject(getQuery(), params, (rs, rowNum) -> {
                return rs.getInt("user_request_id");
            });
            if (ObjectHelper.objectIsNull(id)) {
                setMessageForLogger("return generated key is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(id, REPOSITORY_SUCCESS, null);
    }
}