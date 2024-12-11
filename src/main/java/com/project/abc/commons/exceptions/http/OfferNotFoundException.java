package com.project.abc.commons.exceptions.http;

import com.project.abc.commons.exceptions.ExType;
import com.project.abc.commons.exceptions.ExceptionType;

public class OfferNotFoundException extends BaseException{
    public OfferNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public OfferNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}
