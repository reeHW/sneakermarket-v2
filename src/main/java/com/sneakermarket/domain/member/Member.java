package com.sneakermarket.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;                        // PK

    @Column(nullable = false)
    private String nickname;                // 닉네임
    @Column(nullable = false, unique = true, length = 50)
    private String username;                   // 유저 아이디
    @Column(nullable = false, length = 100)
    private String password;                // 비밀번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    private LocalDateTime createdDate;     // 생성일시
    private LocalDateTime modifiedDate;    // 최종 수정일시

    @Builder
    public Member(String nickname, String username, String password) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
    }

    @PrePersist //최초 Persist될 때 수행
    public void setCreateTime() {
        this.createdDate = LocalDateTime.now();
    }

}
