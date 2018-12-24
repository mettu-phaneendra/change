package com.change.healthcare.service;

import com.change.healthcare.exception.InvalidInputException;
import com.change.healthcare.utils.PasswordValidationType;
import com.change.healthcare.utils.ValidationUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ValidationServiceImpl implements ValidationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ValidationUtil validationUtil;

    /**
     * validate the password, services
     *
     * @param password
     */

    @Override
    public void validatePassword(String password, Map<String, Object> body) throws Exception {
        List<String> errors = new ArrayList<>();
        try {
            Map<String, Boolean> results = validationUtil.validate(password);
            for (String key : results.keySet()) {
                PasswordValidationType type = PasswordValidationType.fromString(key);
                if (null == type) {
                    logger.error("Un-recognized validation, ignore and continue");
                    continue;
                }
                Boolean value = results.get(key);
                switch (type) {
                    case ALPHA_NUMERIC:
                        if (!value) {
                            errors.add("Error: Password must consist of a mixture of lowercase letters"
                                    + " and numerical digits only, with at least one of each.");
                        }
                        break;
                    case LENGTH:
                        if (!value) {
                            errors.add("Error: Password must be between 5 and 12 characters in length");
                        }
                        break;
                    case SEQUENCE:
                        if (!value) {
                            errors.add("Error: Password must not contain any sequence of characters " +
                                    "immediately followed by the same sequence");
                        }
                        break;
                    default:
                        logger.error("Un-recognized validation, ignore and continue");
                }
            }
        } catch (Exception ex) {
            logger.error("Error validating the given entity");
            errors.add(ExceptionUtils.getRootCauseMessage(ex));
        }
        if (!errors.isEmpty()) {
            body.put("errors", errors);
            throw new InvalidInputException("Password validation failed");
        }
    }

}
