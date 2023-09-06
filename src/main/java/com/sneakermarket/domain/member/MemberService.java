package com.sneakermarket.domain.member;

import com.sneakermarket.util.exception.CustomException;
import com.sneakermarket.util.exception.ErrorCode;
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
     * exception : 존재하는 아이디라면 ErrorCode.USERNAME_DUPLICATED
     */
    public boolean existsByUsername(final String username) {
        memberRepository.findByUsername(username).ifPresent(m -> {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATED);
        });
        return true;
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
