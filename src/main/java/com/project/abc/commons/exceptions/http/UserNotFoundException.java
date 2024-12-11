package com.project.abc.commons.exceptions.http;


import com.project.abc.commons.exceptions.ExType;
import com.project.abc.commons.exceptions.ExceptionType;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public UserNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}
