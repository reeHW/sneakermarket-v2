package com.sneakermarket.domain.member;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class MemberDto {

    @NotBlank(message = "이메일을 입력해주세요!")
    @Email(message = "이메일 형식으로 입력새주세요!")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요!")
    @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요!")
    @Size(min =2, max=20, message = "닉네임은 2자 이상, 20자 이하로 입력해주세요.")
    private String nickname;

    @Builder
    public MemberDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
