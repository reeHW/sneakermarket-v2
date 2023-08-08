package com.sneakermarket.domain.member;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


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


    /**
     * 인증된 사용자 정보를 세션에 저장하기 위한 클래스
     * 세션을 저장하기 위해 User 엔티티 클래스를 직접 사용하게 되면 직렬화를 해야 하는데,
     * 엔티티 클래스에 직렬화를 넣어주면 추후에 다른 엔티티와 연관관계를 맺을시
     * 직렬화 대상에 다른 엔티티까지 포함될 수 있어 성능 이슈 우려가 있기 때문에
     * 세션 저장용 Dto 클래스 생성
     * */
    @Getter
    public static class Response implements Serializable {
        private Long id;
        private String username;
        private String nickname;
        private Role role;


       /* Entity -> Dto*/
        public Response(Member member) {
            this.id = member.getId();
            this.username = member.getUsername();
            this.nickname = member.getNickname();
            this.role = member.getRole();
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
