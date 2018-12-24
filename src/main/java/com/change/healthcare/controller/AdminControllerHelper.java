package com.change.healthcare.controller;

import com.change.healthcare.exception.InvalidInputException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AdminControllerHelper {

    @SuppressWarnings("unchecked")
    protected ResponseEntity<Object> handleGeneralException(Exception e, Map<String, Object> payload) {
        if (null == payload) {
            payload = new HashMap<>();
        }
        List<String> errors = (List<String>) payload.get("errors");
        errors = (null == errors) ? new ArrayList<>() : errors;
        String rootCause = ExceptionUtils.getRootCauseMessage(e);
        rootCause = (null == rootCause) ? e.getMessage() : rootCause;
        errors.add(rootCause);
        if (!errors.isEmpty()) {
            payload.put("errors", errors);
        }
        if (e instanceof SQLException) {
            return new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (e instanceof ResourceNotFoundException) {
            return new ResponseEntity<>(payload, HttpStatus.NOT_FOUND);
        }
        if (e instanceof NullPointerException) {
            return new ResponseEntity<>(payload, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (e instanceof UnsupportedOperationException) {
            return new ResponseEntity<>(payload, HttpStatus.METHOD_NOT_ALLOWED);
        }
        if (e instanceof IllegalArgumentException) {
            return new ResponseEntity<>(payload, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (e instanceof InvalidInputException) {
            return new ResponseEntity<>(payload, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}