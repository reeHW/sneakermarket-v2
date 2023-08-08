package com.sneakermarket.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /* Security */
    Optional<Member> findByUsername(String username);

    /* member GET */
    Member findByNickname(String nickname);

    /* 중복인 경우 true, 중복이 아닌 경우 false 반환 */
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

}
