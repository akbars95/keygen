package com.mtsmda.keygen.desktop.repository;

import com.mtsmda.helper.ExceptionMessageHelper;
import com.mtsmda.helper.ListHelper;
import com.mtsmda.helper.LocalDateTimeHelper;
import com.mtsmda.helper.ObjectHelper;
import com.mtsmda.keygen.desktop.model.User;
import com.mtsmda.keygen.desktop.object.response.CommonResponse;
import com.mtsmda.keygen.desktop.rowMapper.UserRowMapper;
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
import static com.mtsmda.helper.QueryCreatorHelper.selectAll;
import static com.mtsmda.helper.QueryCreatorHelper.selectById;
import static com.mtsmda.keygen.desktop.object.response.CommonResponse.*;

/**
 * Created by MTSMDA on 16.09.2016.
 */
@Repository("userRepositoryImpl")
public class UserRepositoryImpl extends RepositoryParent implements UserRepository {

    private static final Logger LOGGER = Logger.getLogger(UserRepositoryImpl.class);

    @Autowired
    @Qualifier("shaPasswordEncoder")
    private ShaPasswordEncoder shaPasswordEncoder;

    @Override
    public CommonResponse<Boolean> insertUser(User user) {
        try {
            if (ObjectHelper.objectIsNull(user)) {
                setMessageForLogger("user " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
            }
            setQuery(insertGenerate(T_USERS, getListWithData(T_USERS_F_USER_EMAIL, T_USERS_F_USER_CREATED_DATE_TIME, T_USERS_F_USER_USERNAME, T_USERS_F_USER_PASSWORD)));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USERS_F_USER_EMAIL, user.getUserEmail());
            params.put(T_USERS_F_USER_CREATED_DATE_TIME, LocalDateTimeHelper.localDateTimeMySqlFormat(user.getUserCreatedUser()));
            params.put(T_USERS_F_USER_USERNAME, user.getUserName());
            params.put(T_USERS_F_USER_PASSWORD, shaPasswordEncoder.encodePassword(user.getUserPassword(), null));
            int update = namedParameterJdbcTemplate.update(getQuery(), params);
            if (update <= 0) {
                setMessageForLogger(MESSAGE_CANNOT_INSERT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(dbExceptionHandler(e) + " Every email can have multiple users, but cannot registrate 2 users with the same email and username!");
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(false, REPOSITORY_INSERT, getMessageForLogger());
        }
        return new CommonResponse<>(true, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<User> getUserByUserEmailOrUsernameAndUserPassword(User user) {
        User responseUser = null;
        try {
            if (ObjectHelper.objectIsNull(user)) {
                setMessageForLogger("user " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
            setQuery("SELECT u.*\n" +
                    "FROM users u\n" +
                    "where (u.user_email = :user_email or u.user_username = :user_username) and u.user_password = :user_password and u.user_enabled = 1;");
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USERS_F_USER_EMAIL, user.getUserEmail());
            params.put(T_USERS_F_USER_USERNAME, user.getUserName());
            params.put(T_USERS_F_USER_PASSWORD, shaPasswordEncoder.encodePassword(user.getUserPassword(), null));
            responseUser = namedParameterJdbcTemplate.queryForObject(getQuery(), params, new UserRowMapper());
            if (ObjectHelper.objectIsNull(responseUser)) {
                setMessageForLogger("return responseUser is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(dbExceptionHandler(e) + "! " + user.getUserEmail() + " have more than 1 users. Solution!" +
                    "Can you please write user email and username or only username.");
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(responseUser, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<User> getUserByUserId(Integer userId) {
        User responseUser = null;
        try {
            if (ObjectHelper.objectIsNull(userId)) {
                setMessageForLogger("userId " + MESSAGE_NULL_OBJECT);
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
            setQuery(selectById(T_USERS, T_USERS_F_USER_ID));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            Map<String, Object> params = new LinkedHashMap<>();
            params.put(T_USERS_F_USER_ID, userId);
            responseUser = namedParameterJdbcTemplate.queryForObject(getQuery(), params, new UserRowMapper());
            if (ObjectHelper.objectIsNull(responseUser)) {
                setMessageForLogger("return responseUser is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET, getMessageForLogger());
        }
        return new CommonResponse<>(responseUser, REPOSITORY_SUCCESS, null);
    }

    @Override
    public CommonResponse<List<User>> getAllUsers() {
        List<User> users = null;
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            setQuery(selectAll(T_USERS));
            LOGGER.info(MESSAGE_LOGGER_QUERY + getQuery());
            users = namedParameterJdbcTemplate.query(getQuery(), params, new UserRowMapper());
            if (ListHelper.listIsNullOrEmpty(users)) {
                setMessageForLogger("return users is null!");
                LOGGER.error(getMessageForLogger());
                return new CommonResponse<>(null, REPOSITORY_GET_ALL, getMessageForLogger());
            }
        } catch (Exception e) {
            setMessageForLogger(ExceptionMessageHelper.exceptionDescription(e));
            LOGGER.error(getMessageForLogger());
            return new CommonResponse<>(null, REPOSITORY_GET_ALL, getMessageForLogger());
        }
        return new CommonResponse<>(users, REPOSITORY_SUCCESS, null);
    }

}