package com.sneakermarket.domain.chat;

import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.global.config.auth.LoggedInMember;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @GetMapping({"/dashboard/myChat", "/dashboard/myChat/{roomId}/{nickname}"})
    public String myChat(@LoggedInMember MemberDto.Response member, Model model){
        model.addAttribute("loggedInMember", member.getNickname());
        return "dashboard/chat";
    }
}
