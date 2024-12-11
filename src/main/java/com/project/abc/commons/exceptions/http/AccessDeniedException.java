package com.project.abc.commons.exceptions.http;

import com.project.abc.commons.exceptions.ExType;
import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BaseException {

    public AccessDeniedException() {
        setType(ExType.ACCESS_DENIED);
    }

    public AccessDeniedException(Exception rootException, String message, Object... params) {
        super(rootException, message, params);
        setType(ExType.ACCESS_DENIED);
    }

    public AccessDeniedException(String message, Object... params) {
        super(message, params);
        setType(ExType.ACCESS_DENIED);
    }

    public AccessDeniedException(String message) {
        super(message);
        setType(ExType.ACCESS_DENIED);
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.FORBIDDEN;
    }
}
