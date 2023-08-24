package com.sneakermarket.config.oauth;

import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*현재 로그인 진행 중인 서비스를 구분하는 코드 - 구글, 네이버*/
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        /**
         * userNameAttributeName
         * - OAuth2 로그인 진행 시 키가 되는 필드값 (Primary Key)
         * - 구글의 경우 기본적으로 코드 지원 but, 네이버, 카카오 등은 지원하지 않음
         * - 구글 기본 코드는 "sub"
         * - 이후 네이버, 구글 로그인을 동시 지원할 때 사용됨
         */

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);

        //  세션에 사용자 정보를 저장하기 위해 직렬화된 Dto 클래스 사용.
        httpSession.setAttribute("member", new MemberDto.Response(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    /* 소셜 로그인 시 기존 사용자 정보가 업데이트 되었을 때 Member 엔티티도 update */
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByUsername(attributes.getUsername())
                .map(entity -> entity.update(attributes.getNickname()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
