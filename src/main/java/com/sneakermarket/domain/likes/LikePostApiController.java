package com.sneakermarket.domain.likes;

import com.sneakermarket.global.config.auth.LoggedInMember;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.post.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likepost")
public class LikePostApiController {
    private final LikePostService likePostService;

    /**
     * 관심 게시물 등록
     */
    @PostMapping("/{postId}")
    public ResponseEntity<?> like(@PathVariable Long postId, @LoggedInMember MemberDto.Response member){
        int likeCnt = likePostService.like(member, postId);
        return ResponseEntity.status(HttpStatus.OK).body(likeCnt);
    }

    /**
     * 관심 게시물 해제
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> unlike(@PathVariable Long postId, @LoggedInMember MemberDto.Response member){
        int likeCnt = likePostService.unlike(member, postId);
        return ResponseEntity.status(HttpStatus.OK).body(likeCnt);
    }

    /**
     * 회원별 관심 게시물 목록
     */
    @GetMapping("/list")
    public ResponseEntity<?> list(@LoggedInMember MemberDto.Response member, @PageableDefault(size=10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        PageImpl<PostDto.Response> list  = likePostService.list(member, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
