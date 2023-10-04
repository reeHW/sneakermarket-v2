package com.sneakermarket.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;


    /**
     * 회원 정보 저장(회원 가입)
     * @param params - 회원정보
     * @return PK
     */

    @Transactional
    public Long memberJoin(final MemberDto.RegisterForm params) {

        params.setPassword(encoder.encode(params.getPassword()));

        return memberRepository.save(params.toEntity()).getId();
    }


    /**
     * username(id) 중복 체크
     * @param  username - id
     *
     */
    public boolean existsByUsername(final String username) {
        return memberRepository.existsByUsername(username);
    }

    /**
     * 닉네임 중복 체크
     * @param nickname
     * @return 회원 수
     */
    public boolean existsByNickname(final String nickname) {
        return memberRepository.existsByNickname(nickname);
    }




}
