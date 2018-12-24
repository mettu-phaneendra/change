package com.change.healthcare.service;

import com.change.healthcare.exception.InvalidInputException;
import com.change.healthcare.utils.PasswordValidationType;
import com.change.healthcare.utils.ValidationUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ValidationServiceImplTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @TestConfiguration
    static class ValidationServiceImplTestContextConfiguration {
        @Bean
        public ValidationService validationService() {
            return new ValidationServiceImpl();
        }
    }

    @Autowired
    private ValidationService validationService;

    @MockBean
    private ValidationUtil validationUtil;

    @Test
    public void testValidatePasswordHappyPath() {
        try {
            Map<String, Object> input = new HashMap<>();
            validationService.validatePassword("abcdefgh123", input);
            assertThat(input.isEmpty());
        } catch (Exception ex) {
            logger.error("Exception happened while testing the service");
            logger.error(ExceptionUtils.getRootCauseMessage(ex));
        }

    }

    @Test(expected= InvalidInputException.class)
    public void testValidatePasswordAlphaNumeric() throws Exception {
        String password = "abcdef";
        Map<String, Boolean> dto = new HashMap<>();
        dto.put(PasswordValidationType.ALPHA_NUMERIC.getType(), false);
            Mockito.when(validationUtil.validate(password)).thenReturn(dto);
            Map<String, Object> input = new HashMap<>();
            validationService.validatePassword(password, input);
    }

    @Test(expected= InvalidInputException.class)
    public void testValidatePasswordLength() throws Exception {
        String password = "ab1";
        Map<String, Boolean> dto = new HashMap<>();
        dto.put(PasswordValidationType.LENGTH.getType(), false);
        Mockito.when(validationUtil.validate(password)).thenReturn(dto);
        Map<String, Object> input = new HashMap<>();
        validationService.validatePassword(password, input);
    }

    @Test(expected= InvalidInputException.class)
    public void testValidatePasswordSeq() throws Exception {
        String password = "ababab1";
        Map<String, Boolean> dto = new HashMap<>();
        dto.put(PasswordValidationType.SEQUENCE.getType(), false);
        Mockito.when(validationUtil.validate(password)).thenReturn(dto);
        Map<String, Object> input = new HashMap<>();
        validationService.validatePassword(password, input);
    }

}
