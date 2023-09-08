package com.sneakermarket.util.exception;

import com.sneakermarket.util.error.ErrorResponse;

public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
    private ErrorResponse errorResponse;


    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}