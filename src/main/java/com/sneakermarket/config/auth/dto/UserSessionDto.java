package com.sneakermarket.config.auth.dto;

import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.Role;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable {
    private String username;
    private String nickname;
    private String password;
    private Role role;

    /* Entity -> Dto */
    public UserSessionDto(Member member){
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.password = member.getPassword();
        this.role = member.getRole();
    }
}
