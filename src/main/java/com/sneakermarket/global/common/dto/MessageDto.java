package com.sneakermarket.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Getter
@AllArgsConstructor
public class MessageDto {
    private String message; // 사옹자에게 전달할 메시지
    private String redirectUri; // 리다이렉트 URI
    private RequestMethod method; // HTTP 요청 메서드
    private Map<String, Object> data; // view로 전달할 데이터(파라미터)
}
