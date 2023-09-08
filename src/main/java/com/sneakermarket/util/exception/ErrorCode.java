package com.sneakermarket.util.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    ID_NOT_FOUND("id", "id.notfound", "해당 id를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    ONLY_MEMBER("access", "access.denied", "회원만 기능을 이용할 수 있습니다.", HttpStatus.BAD_REQUEST),

    //member
    USERNAME_DUPLICATED("username", "username.duplicated", "이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_DUPLICATED("nickname", "nickname.duplicated", "이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST);


    private String cause;
    private String code;
    private String message;
    private HttpStatus httpStatus;

}