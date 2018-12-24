package com.change.healthcare.controller;


import com.change.healthcare.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/validate")
public class ValidationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AdminControllerHelper adminController;

    /**
     * Validate the password, with the given rules.
     * <p>
     * Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.
     * Must be between 5 and 12 characters in length.
     * Must not contain any sequence of characters immediately followed by the same sequence.
     *
     * @param password
     * @return validation results on success
     */
    @RequestMapping(path = "/password/{password}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> validatePassword(@PathVariable("password") String password) {
        logger.info("Validate user password");
        Map<String, Object> body = new HashMap<>();
        try {
            validationService.validatePassword(password, body);
        } catch (Exception ex) {
            return adminController.handleGeneralException(ex, body);
        }
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
