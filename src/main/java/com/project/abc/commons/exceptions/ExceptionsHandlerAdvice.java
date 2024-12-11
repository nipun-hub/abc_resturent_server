package com.project.abc.commons.exceptions;

import com.project.abc.commons.RequestDataProvider;
import com.project.abc.commons.exceptions.http.BaseException;
import com.project.abc.commons.exceptions.http.InternalErrorException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionsHandlerAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    RequestDataProvider requestDataProvider;

    @ExceptionHandler(value = { BaseException.class})
    protected ResponseEntity<Object> handleCustomException(BaseException exception, HttpServletRequest request) {
        log.error("exception occurred [BaseException] type = {} error = {}",exception.getClass().getCanonicalName(), exception.getMessage());
        exception.printStackTrace();
        return exception.getJsonAsResponse(requestDataProvider.getRequestHash());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception exception) {
        log.error("exception occurred [Exception] error = {}", exception.getMessage());
        exception.printStackTrace();
        var internalExp = new InternalErrorException(exception.getMessage());
        return internalExp.getJsonAsResponse(requestDataProvider.getRequestHash());
    }

//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        log.error("exception occurred [Malformed exception] error = {}", ex.getMessage());
//        log.info("http response url = {}, response = {}",  request.getContextPath(), status.value());
//        ex.printStackTrace();
//        var exception = new BadRequestException("malformed request body. Please check for correct date/time formats and data types");
//        return exception.getJsonAsResponse(requestDataProvider.getRequestHash());
//    }
}
