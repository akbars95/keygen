package com.mtsmda.keygen.desktop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Created by dminzat on 9/9/2016.
 */
public class RepositoryParent {

    protected static final String MESSAGE_NULL_OBJECT = "object is null!";
    protected static final String MESSAGE_CANNOT_INSERT = "cannot insert!";
    protected static final String MESSAGE_CANNOT_UPDATE = "cannot update!";
    protected static final String MESSAGE_CANNOT_DELETE = "cannot delete!";
    protected static final String MESSAGE_LOGGER_QUERY = "query - ";

    @Autowired
    @Qualifier("namedParameterJdbcTemplate")
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String messageForLogger;
    private String query;

    protected String getMessageForLogger() {
        return messageForLogger;
    }

    protected void setMessageForLogger(String messageForLogger) {
        this.messageForLogger = messageForLogger;
    }

    protected String getQuery() {
        return query;
    }

    protected void setQuery(String query) {
        this.query = query;
    }

    protected <T extends Exception> String dbExceptionHandler(T exception) {
        if (exception.getClass().getCanonicalName().equals(DuplicateKeyException.class.getCanonicalName())) {
            return "Duplicate data!";
        } else if (exception.getClass().getCanonicalName().equals(IncorrectResultSizeDataAccessException.class.getCanonicalName())) {
            return "Actual size " + ((IncorrectResultSizeDataAccessException) exception).getActualSize();
        }
        return "";
    }

}