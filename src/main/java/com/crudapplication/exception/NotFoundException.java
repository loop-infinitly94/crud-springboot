package com.crudapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private String resource;
    private String fieldName;
    private Object fieldValue;


    public NotFoundException(String resource, String fieldName, Object fieldValue){
        super(String.format("%s not found with %s : %s", resource, fieldName, fieldValue));
        this.resource = resource;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
