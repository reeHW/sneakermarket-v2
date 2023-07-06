package com.sneakermarket.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private Long id;                        // PK
    private String nickname;                // 닉네임
    private String email;                   // 이메일
    private String password;                // 비밀번호
    private Boolean deleteYn;              // 삭제 여부
    private LocalDateTime createdDate;     // 생성일시
    private LocalDateTime modifiedDate;    // 최종 수정일시


    @Builder
    public Member(Long id, String nickname, String email, String password, Boolean deleteYn, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.deleteYn = deleteYn;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder){
        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword())) // 암호화 처리
                .nickname(memberDto.getNickname())
                .build();
        return member;
    }
}
