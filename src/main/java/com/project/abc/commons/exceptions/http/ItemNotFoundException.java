package com.project.abc.commons.exceptions.http;

import com.project.abc.commons.exceptions.ExType;
import com.project.abc.commons.exceptions.ExceptionType;

public class ItemNotFoundException extends BaseException {

    public ItemNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public ItemNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}