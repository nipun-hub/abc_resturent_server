package com.project.abc.commons;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.project.abc.commons.exceptions.http.BadRequestException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class BaseRequest {
    @JsonIgnore
    private List<String> errors = new ArrayList<>();

    @JsonIgnore
    public boolean validate() {
        if (_hasError(null)) {
            log.error("request parameters validation fail. message={}", getErrorsAsString());
            throw new BadRequestException("INVALIDATE_REQUEST", this.errors);
        }
        return false;
    }

    @JsonIgnore
    public boolean validate(Class[] groups) {
        if (_hasError(groups)) {
            log.error("request parameters validation fail. message={}", getErrorsAsString());
            throw new BadRequestException("INVALIDATE_REQUEST", this.errors);
        }
        return false;
    }

    @JsonIgnore
    public void setError(String error) {
        this.errors.add(error);
    }

    @JsonIgnore
    public String getErrorsAsString() {
        String error = this.errors.stream().reduce((s, s2) -> s + ", " + s2).get();
        log.info("getting error as string, error={}", error);
        return error;
    }

    @JsonIgnore
    private boolean _hasError(Class[] groups) {
        if (groups == null) {
            groups = new Class[0];
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BaseRequest>> validate = validator.validate(this, groups);
        if (validate.size() > 0) {
            for (ConstraintViolation<BaseRequest> err : validate) {
                setError(err.getMessage());
            }
            return true;
        }
        return false;
    }

}
