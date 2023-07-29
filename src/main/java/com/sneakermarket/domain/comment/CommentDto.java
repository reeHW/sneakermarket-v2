package com.sneakermarket.domain.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class CommentDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditForm{

        private Long id;                       // 댓글 번호 (PK)
        private Long postId;                   // 게시글 번호 (FK)
        @NotBlank(message = "댓글 내용을 입력해주세요")
        private String content;                // 내용
        private String writer;                 // 작성자
        private LocalDateTime createdDate;     // 생성일시

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private Long id;                       // 댓글 번호 (PK)
        private Long postId;                   // 게시글 번호 (FK)
        private String content;                // 내용
        private String writer;                 // 작성자
        private LocalDateTime createdDate;     // 생성일시


        public Response(Comment comment){
            this.id = comment.getId();
            this.postId = comment.getPostId();
            this.content = comment.getContent();
            this.writer = comment.getWriter();
            this.createdDate = comment.getCreatedDate();
        }
    }

    public static List<CommentDto.Response> CommentListToDto(List<Comment> comments){
        return comments.stream()
                .map(comment -> new CommentDto.Response(comment)).collect(Collectors.toList());
    }



}