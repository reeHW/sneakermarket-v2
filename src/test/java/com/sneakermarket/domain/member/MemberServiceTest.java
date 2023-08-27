package com.sneakermarket.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입 테스트")
    void join(){
        //given
        MemberDto.RegisterForm memberDto = MemberDto.RegisterForm.builder()
                .username("test1")
                .nickname("테스터")
                .password("test123@")
                .build();

        //when
        Long memberId = memberService.memberJoin(memberDto);

        //then
        assertThat(memberId).isNotNull();
        Member byUsername = memberRepository.findByUsername("test1").orElse(null);
        assertThat(byUsername.getNickname()).isEqualTo("테스터");
    }
}
