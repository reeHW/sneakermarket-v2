package com.sneakermarket.domain.member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    /**
     * 회원 상세정보 조회
     * @param email - UK
     * @return 회원 상세정보
     */
    Member findByEmail(String email);

    /**
     * 회원 정보 수정
     * @param params - 회원정보
     */
    void update(Member params);

    /**
     * 회원 정보 삭제(회원 탈퇴)
     * @param email = UK
     */
    void deleteByEmail(String email);

    /**
     * 회원 정보 저장 (회원가입)
     * @param member
     */
    void save(Member member);



    /**
     * 회원 수 카운팅 (email 중복 체크)
     * @param param
     * @return 회원 수
     */
    int countMemberByEmail(String param);

    /**
     * 회원 수 카운팅 (닉네임 중복 체크)
     * @param param
     * @return 회원 수
     */
    int countMemberByNickname(String param);
}
