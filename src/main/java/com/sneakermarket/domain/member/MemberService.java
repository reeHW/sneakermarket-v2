package com.sneakermarket.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;


    // 비밀번호 인코딩
    public String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateDuplicateMember(final Member member) {
        Member findMember = memberMapper.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    /**
     * 로그인
     * @param email - 로그인 Email
     * @param password - 비밀번호
     * @return 회원 상세정보
     */

    public MemberDto.FindForm login(final String email, final String password){

        //1. 회원 정보 및 비밀번호 조회
        Member member = findByEmail(email);
        String encodePassword = (member == null) ? "" : member.getPassword();

         //2. 회원 정보 및 비밀번호 체크
        if(member == null || passwordEncoder.matches(password, encodePassword) == false){
            return null;
        }

        //3. 회원 정보 리턴
        return new MemberDto.FindForm(email, encodePassword);

    }

    /**
     * 회원 정보 저장(회원 가입)
     * @param params - 회원정보
     * @return PK
     */

    @Transactional
    public void saveMember(final MemberDto.RegisterForm params) {
        //DTO -> Entity
        Member member = new Member(
                params.getNickname(),
                params.getEmail(),
                encodingPassword(params.getPassword())
        );
        validateDuplicateMember(member);
        memberMapper.save(member);
    }

    /**
     * 회원 상세정보 조회
     * @param email - UK
     * @return 회원 상세정보
     */
    public Member findByEmail(final String email){
        return memberMapper.findByEmail(email);

    }

    /**
     * 회원 정보 수정
     * @param params - 회원 정보
     * @return PK
     */
    @Transactional
    public String updateMember(final MemberDto.UpdateForm params){
        Member member = new Member(
                params.getNickname(),
                params.getEmail(),
                encodingPassword(params.getNewPassword())
        );
        memberMapper.update(member);
        return params.getEmail();
    }

    /**
     * 회원 정보 삭제(회원 탈퇴)
     * @param email - UK
     * @return UK
     */
    @Transactional
    public String deleteMemberByEmail(final String email){
        memberMapper.deleteByEmail(email);
        return email;
    }


}
