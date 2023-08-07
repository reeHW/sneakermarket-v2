package com.sneakermarket.domain.member;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class MemberDto {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RegisterForm{

        @NotBlank(message = "아이디를 입력해주세요!")
        @Size(min = 6, max = 12, message = "아이디는 6자 이상, 12자의 영문, 숫자만 가능합니다.")
        private String username;

        @NotBlank(message = "비밀번호를 입력해주세요!")
        @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요!")
        @Size(min = 2, max = 20, message = "닉네임은 2자 이상, 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣_]*$", message = "특수문자는 불가능합니다")
        private String nickname;



        public Member toEntity(){
            return Member.builder()
                    .username(username)
                    .password(password)
                    .nickname(nickname)
                    .build();
        }

    }

    @Getter
    @Setter
    public static class FindForm {
        private Long id;
        private String username;
        private String nickname;


        public FindForm(Long id, String email, String nickname) {
            this.id = id;
            this.username = email;
            this.nickname = nickname;
        }
    }

    @Getter
    @Setter
    public static class UpdateForm{

        private String username;

        @Size(min = 2, max = 20, message = "닉네임은 2자 이상, 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣_]*$", message = "특수문자는 불가능합니다")
        private String nickname;

        @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
        private String oldPassword;

        @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
        private String newPassword;


    }



}
