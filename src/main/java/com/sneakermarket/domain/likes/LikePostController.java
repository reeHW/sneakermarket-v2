package com.sneakermarket.domain.likes;

import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.global.config.auth.LoggedInMember;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LikePostController {

    @GetMapping("dashboard/favorite")
    public String openFavoritePosts(@LoggedInMember MemberDto.Response member, Model model){
        if(member != null){
            model.addAttribute("loggedInMember", member.getNickname());
        }

        return "dashboard/favorite-posts";
    }


}
