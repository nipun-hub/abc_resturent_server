package com.project.abc.commons.exceptions.http;

import com.project.abc.commons.exceptions.ExType;
import com.project.abc.commons.exceptions.ExceptionType;

public class InquiryNotFoundException extends BaseException {

    public InquiryNotFoundException(ExceptionType exType , String message){
        super(exType, message);
    }

    public InquiryNotFoundException(String message){
        super(message);
        setType(ExType.NOT_FOUND);
    }
}
