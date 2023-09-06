package com.sneakermarket.util.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

public enum ErrorCode {

    ID_NOT_FOUND("id", "id.notfound", "해당 id를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

    //member
    USERNAME_DUPLICATED("username", "username.duplicated", "아이디가 중복됩니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_DUPLICATED("nickname", "nickname.duplicated", "닉네임이 중복됩니다.", HttpStatus.BAD_REQUEST);


    private String cause;
    private String code;
    private String message;
    private HttpStatus httpStatus;

    private ErrorCode(String cause, String code, String message, HttpStatus httpStatus) {
        this.cause = cause;
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCause() {
        return cause;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}