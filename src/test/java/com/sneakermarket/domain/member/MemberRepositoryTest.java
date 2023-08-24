package com.sneakermarket.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void createMember(){
        String username = "hwhw";
        String nickname = "테스트2";
        String rawPassword = "qlalfqjsgh12@";
        String encPassword = encoder.encode(rawPassword);
        memberRepository.save(Member.builder()
                        .username(username)
                        .nickname(nickname)
                        .password(encPassword)
                        .build());
        List<Member> memberList = memberRepository.findAll();

        Member member = memberList.get(7);

        assertThat(member.getUsername()).isEqualTo(username);
        assertThat(member.getPassword()).isEqualTo(encPassword);

        memberRepository.delete(member);

    }
}
