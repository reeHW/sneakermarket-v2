package com.sneakermarket.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/* 세션 관련 중복 코드 제거를 위해 생성 */
@Target(ElementType.PARAMETER) // 어노테이션이 생성될 수 있는 위치
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggedInMember {
}
