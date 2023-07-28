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
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RegisterForm{

        private Long id;

        @NotBlank(message = "이메일을 입력해주세요!")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호를 입력해주세요!")
        @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
        private String password;

        @NotBlank(message = "닉네임을 입력해주세요!")
        @Size(min = 2, max = 20, message = "닉네임은 2자 이상, 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣_]*$", message = "특수문자는 불가능합니다")
        private String nickname;

    }

    @Getter
    @Setter
    public static class FindForm {
        private Long id;
        private String email;
        private String nickname;
        //private Boolean deleteYn;

        public FindForm(Long id, String email, String nickname) {
            this.id = id;
            this.email = email;
            this.nickname = nickname;
        }
    }

    @Getter
    @Setter
    public static class UpdateForm{

        @Email(message = "이메일 형식으로 입력새주세요.")
        private String email;

        @Size(min = 2, max = 20, message = "닉네임은 2자 이상, 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣_]*$", message = "특수문자는 불가능합니다")
        private String nickname;

        @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
        private String oldPassword;

        @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요.")
        private String newPassword;


    }



}
