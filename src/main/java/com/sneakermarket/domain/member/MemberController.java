package com.sneakermarket.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    // 회원 정보 저장 (회원가입)
    @PostMapping("/auth/joinProc")
    @ResponseBody
    public ResponseEntity join(@Valid @RequestBody final MemberDto.RegisterForm params, BindingResult bindingResult) {

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
        memberService.memberJoin(params);
        return ResponseEntity.ok(params);


    }

    // 로그인 페이지
    @GetMapping("/auth/login")
    public String openLogin(@RequestParam(value = "error", required = false)String error, @RequestParam(value = "exception", required = false) String exception, Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "member/login";
    }


    // 아이디 중복 체크
    @GetMapping("auth/member-username-check")
    @ResponseBody
    public boolean existsByUsername(@RequestParam final String username) {
        return memberService.existsByUsername(username);
    }

    // 닉네임 중복 체크
    @GetMapping("auth/member-nickname-check")
    @ResponseBody
    public boolean existsByNickname(@RequestParam final String nickname) {
        return memberService.existsByNickname(nickname);
    }

}