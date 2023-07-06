package com.sneakermarket.domain.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    /**
     * 회원 정보 저장 (회원가입)
     * @param member
     */
    void save(Member member);

    /**
     * 회원 상세정보 조회
     * @param email - UK
     * @return 회원 상세정보
     */
    Member findByEmail(String email);

}
