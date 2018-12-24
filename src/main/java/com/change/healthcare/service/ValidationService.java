package com.change.healthcare.service;

import java.util.Map;

public interface ValidationService {

    void validatePassword(String password, Map<String, Object> body) throws Exception;

}
