package com.sneakermarket.domain.comment;

import com.sneakermarket.common.paging.PagingResponse;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.security.auth.LoggedInMember;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;


    //신규 댓글 생성
    @PostMapping("/posts/{postId}/comments")
    public CommentDto.Response saveComment(@PathVariable final Long postId
            , @RequestBody final CommentDto.EditForm params, @LoggedInMember MemberDto.Response member){

        Long id = commentService.saveComment(member.getNickname(), postId, params);
        return commentService.findCommentById(id);
    }


    // 기존 댓글 수정
    @PatchMapping("/posts/{postId}/comments/{id}")
    public CommentDto.Response updateComment(@PathVariable final Long postId, @PathVariable final Long id, @RequestBody final CommentDto.EditForm params) {

        commentService.updateComment(id, params);
        return commentService.findCommentById(id);
    }

    // 댓글 리스트 조회
    @GetMapping("/posts/{postId}/comments")
    public PagingResponse<CommentDto.Response> findAllComment(@PathVariable final long postId, final CommentSearchDto params, @LoggedInMember MemberDto.Response member){
        return commentService.findAllComment(params, member);
    }

    // 댓글 상세정보 조회
    @GetMapping("/posts/{postId}/comments/{id}")
    public CommentDto.Response findCommentById(@PathVariable final Long postId, @PathVariable final Long id) {

        return commentService.findCommentById(id);


    }


    // 댓글 삭제
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public Long deleteComment(@PathVariable final Long postId, @PathVariable final Long id) {
        return commentService.deleteComment(id);
    }
}
