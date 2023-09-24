package com.sneakermarket.util.exception;

public class CustomException extends RuntimeException{
    private ErrorCode errorCode;
    private ErrorResponse errorResponse = new ErrorResponse();


    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorResponse = new ErrorResponse(errorCode);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}