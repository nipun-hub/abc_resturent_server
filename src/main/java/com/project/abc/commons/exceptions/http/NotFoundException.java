package com.project.abc.commons.exceptions.http;

import com.project.abc.commons.exceptions.ExType;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException() {
        setType(ExType.NOT_FOUND);
    }

    public NotFoundException(Exception rootException, String message, Object... params) {
        super(rootException, message, params);
        setType(ExType.NOT_FOUND);
    }

    public NotFoundException(String message, Object... params) {
        super(message, params);
        setType(ExType.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message);
        setType(ExType.NOT_FOUND);
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }
}
