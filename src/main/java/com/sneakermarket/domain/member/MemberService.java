package com.sneakermarket.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberMapper memberMapper;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        memberMapper.save(member);
        return member;
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberMapper.findByEmail(member.getEmail());
        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
