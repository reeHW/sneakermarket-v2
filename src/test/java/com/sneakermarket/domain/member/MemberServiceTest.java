package com.sneakermarket.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

/*    public void createMember() {
        MemberDto.RegisterForm memberRequestDto = new MemberDto.RegisterForm("test@gmail.com", "1234", "테스터");
        memberService.saveMember(memberRequestDto);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member saveMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), saveMember.getEmail());
    }*/

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncode(){

        //when
        String rawPassword = "12345678";

        //given
        String encodedPassword = passwordEncoder.encode(rawPassword);

        //then
        assertAll(
                () -> assertNotEquals(rawPassword, encodedPassword),
                () -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
        );
    }

}

