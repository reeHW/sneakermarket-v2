package com.sneakermarket.domain.comment;

import com.sneakermarket.common.paging.PagingResponse;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.config.auth.LoggedInMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;


    //신규 댓글 생성
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity saveComment(@PathVariable final Long postId
            , @RequestBody final CommentDto.EditForm params, @LoggedInMember MemberDto.Response member){

        CommentDto.Response savedComment = commentService.saveComment(member, postId, params);
        return ResponseEntity.status(HttpStatus.OK).body(savedComment);
    }


    // 기존 댓글 수정
    @PatchMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity updateComment(@PathVariable final Long postId, @PathVariable final Long id, @RequestBody final CommentDto.EditForm params) {

        commentService.updateComment(id, params);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 댓글 리스트 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity findAllComment(@PathVariable final long postId, final CommentSearchDto params, @LoggedInMember MemberDto.Response member){

        PagingResponse<CommentDto.Response> list = commentService.findAllComment(params, member);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // 댓글 상세정보 조회
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity findCommentById(@PathVariable final Long postId, @PathVariable final Long id) {

        CommentDto.Response comment = commentService.findCommentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(comment);

    }

    // 댓글 삭제
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity ResponseEntity(@PathVariable final Long postId, @PathVariable final Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
