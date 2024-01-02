package com.hanyang.dataportal.exception;

import org.springframework.http.HttpStatus;

import static com.hanyang.dataportal.exception.ErrorCode.ENTITY_NOT_FOUND;

public class EntityNotFoundException extends ApplicationException{
    public EntityNotFoundException(){
        super(ENTITY_NOT_FOUND);
    }
}
