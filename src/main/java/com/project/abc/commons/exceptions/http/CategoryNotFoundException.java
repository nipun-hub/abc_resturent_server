package com.project.abc.commons.exceptions.http;

import com.project.abc.commons.exceptions.ExType;
import com.project.abc.commons.exceptions.ExceptionType;

public class CategoryNotFoundException extends BaseException{

    public CategoryNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public CategoryNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}
