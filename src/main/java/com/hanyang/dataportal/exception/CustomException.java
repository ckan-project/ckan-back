package com.hanyang.dataportal.exception;

import lombok.Getter;

import static com.hanyang.dataportal.exception.ErrorType.DUPLICATE_ID;

@Getter
public abstract class CustomException extends RuntimeException {
    private final ErrorApiResponse errorApiResponse;
    public CustomException(ErrorApiResponse errorApiResponse) {
        this.errorApiResponse = errorApiResponse;
    }

    public static class DuplicateIdException extends CustomException{
        public DuplicateIdException(){
            super(new ErrorApiResponse(DUPLICATE_ID));
        }
    }

    public static class EntityNotFoundException extends CustomException{
        public EntityNotFoundException(String msg){
            super(new ErrorApiResponse(400,msg));
        }
    }

    public static class JwtException extends CustomException{
        public JwtException(String msg){
            super(new ErrorApiResponse(401,msg));
        }
    }


}
