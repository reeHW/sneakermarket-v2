package com.sneakermarket.domain.comment;

import com.sneakermarket.domain.post.entity.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class CommentDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EditForm{

        private Long id;                       // 댓글 번호 (PK)
        private Post post;                   // 게시글 번호 (FK)
        @NotBlank(message = "댓글 내용을 입력해주세요")
        private String content;                // 내용
        private String writer;                 // 작성자


        public Comment toEntity(){
            return Comment.builder()
                    .post(post)
                    .content(content)
                    .writer(writer)
                    .build();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private Long id;                       // 댓글 번호 (PK)
        private Long postId;                   // 게시글 번호 (FK)
        private String content;                // 내용
        private String writer;                 // 작성자
        private LocalDateTime createdDate;     // 생성일시
        private char deleteYn;              // 삭제 여부


        public Response(Comment entity){
            this.id = entity.getId();
            this.postId = entity.getPost().getId();
            this.content = entity.getContent();
            this.writer = entity.getWriter();
            this.createdDate = entity.getCreatedDate();
            this.deleteYn = entity.getDeleteYn();
        }
    }

    public static List<CommentDto.Response> CommentListToDto(List<Comment> comments){
        return comments.stream()
                .map(comment -> new CommentDto.Response(comment)).collect(Collectors.toList());
    }



}