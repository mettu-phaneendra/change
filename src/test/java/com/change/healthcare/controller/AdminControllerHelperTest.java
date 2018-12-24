package com.change.healthcare.controller;

import com.change.healthcare.exception.InvalidInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AdminControllerHelperTest {

    @Test
    public void testHandleGeneralException() {
        AdminControllerHelper helper = new AdminControllerHelper();
        ResponseEntity<Object> obj = helper.handleGeneralException(
                new InvalidInputException("testing the helper"), new HashMap<>());
        assertThat(obj.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}