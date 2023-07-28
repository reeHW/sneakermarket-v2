package com.sneakermarket.domain.member;

import com.sneakermarket.config.SessionConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // 로그인 페이지
    @GetMapping("/login.do")
    public String openLogin() {
        return "member/login";
    }

    //로그인
    @PostMapping("/login")
    @ResponseBody
    public MemberDto.FindForm login(HttpServletRequest request){

        // 1. 회원 정보 조회
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Member member = memberService.findByEmail(email);
        MemberDto.FindForm loginMember = memberService.login(email, password);
        loginMember.setId(member.getId());


        //2. 세션에 회원 정보 저장 & 세션 유지 시간 설정
        if(member != null){
            HttpSession session = request.getSession();
            session.setAttribute(SessionConstants.LOGIN_MEMBER, loginMember);
            session.setMaxInactiveInterval(60*30);
        }
        return loginMember;
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login.do";
    }
/*
    // 회원 가입 실패시 입력값 유지
    @GetMapping(value ="/members")
    public void joinGet(MemberDto.RegisterForm params){
        log.info("Controller joinGet");
    }*/

    // 회원 정보 저장 (회원가입)
    @PostMapping("/members")
    @ResponseBody
    public ResponseEntity saveMember(@Valid @RequestBody final MemberDto.RegisterForm params, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError fieldError = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                System.out.println("field : "+ fieldError.getField()+ ", ");
                System.out.println("message : " + message);

                sb.append("field : ").append(fieldError.getField());
                sb.append("message : ").append(message);
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
        }
        memberService.saveMember(params);
        return ResponseEntity.ok(params);


    }

    // 회원 상세정보 조회
    @GetMapping("/members/{email}")
    @ResponseBody
    public Member findMemberEmail(@PathVariable final String email) {
        return memberService.findByEmail(email);
    }

    // 회원 정보 수정
    @PatchMapping("/members/{email}")
    @ResponseBody
    public String updateMember(@PathVariable final Long email, @RequestBody final MemberDto.UpdateForm params) {
        return memberService.updateMember(params);
    }

    // 회원 정보 삭제 (회원 탈퇴)
    @DeleteMapping("/members/{email}")
    @ResponseBody
    public String deleteMemberByEmail(final String email) {
        return memberService.deleteMemberByEmail(email);
    }

    // 회원 수 카운팅 (email 중복 체크)
    @GetMapping("/member-email-count")
    @ResponseBody
    public int countMemberByEmail(@RequestParam final String email) {
        return memberService.countMemberByEmail(email);
    }

    // 회원 수 카운팅 (닉네임 중복 체크)
    @GetMapping("/member-nickname-count")
    @ResponseBody
    public int countMemberByNickname(@RequestParam final String nickname) {
        return memberService.countMemberByNickname(nickname);
    }

}