package com.project.abc.commons.exceptions.http;

import com.project.abc.commons.exceptions.ExType;
import com.project.abc.commons.exceptions.ExceptionType;
import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

    public ConflictException(Exception rootException, String message, Object... params) {
        super(rootException, message, params);
        setType(ExType.CONFLICT);
    }

    public ConflictException(String message, Object... params) {
        super(message, params);
        setType(ExType.CONFLICT);
    }

    public ConflictException(String message) {
        super(message);
        setType(ExType.CONFLICT);
    }

    public ConflictException(ExceptionType type, String message) {
        super(type, message);
    }

    public ConflictException(ExceptionType type, String message, Object... params) {
        super(type, message, params);
    }

    public ConflictException(Exception rootException, ExceptionType type, String message, Object... params) {
        super(rootException, type, message, params);
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.CONFLICT;
    }
}
