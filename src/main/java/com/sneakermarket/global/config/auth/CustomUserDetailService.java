package com.sneakermarket.global.config.auth;


import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Component
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final HttpSession session;


    /** 스프링이 로그인 요청을 가로챌때 username, password변수 2개를 가로채는데
     *  password 부분 처리는 알아서처리,
     *  username이 DB에 있는지 확인해줘야함*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + username));

        session.setAttribute("member", new MemberDto.Response(member));

        /* 시큐리티 세션에 유저 정보 저장 */
        return new CustomUserDetails(member);
    }


}
