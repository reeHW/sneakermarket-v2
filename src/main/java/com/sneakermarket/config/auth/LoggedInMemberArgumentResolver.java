package com.sneakermarket.config.auth;

import com.sneakermarket.domain.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoggedInMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession session;



     /*Controller 메서드가 특정 파라미터를 지원하는지 판단*/
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoggedInMember.class) != null &&
                parameter.getParameterType().equals(MemberDto.Response.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return session.getAttribute("member");
    }
}
