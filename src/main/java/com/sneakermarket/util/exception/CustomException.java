package com.sneakermarket.util.exception;

import com.sneakermarket.util.error.ErrorResponse;

public class CustomException extends RuntimeException{

    private ErrorCode errorCode;
    private ErrorResponse errorResponse = new ErrorResponse();

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorResponse = new ErrorResponse(errorCode);
    }

    public ErrorResponse getResponse(){
        return errorResponse;
    }

}