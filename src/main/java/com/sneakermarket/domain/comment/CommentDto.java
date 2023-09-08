package com.sneakermarket.domain.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.post.Post;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class CommentDto {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class WriteForm{

        private Long id;                       // 댓글 번호 (PK)
        private Post post;                   // 게시글 번호 (FK)
        @NotBlank(message = "댓글 내용을 입력해주세요")
        private String content;                // 내용
        private String writer;                 // 작성자
        private Member member;

        public WriteForm(String content) {
            this.content = content;
        }

        public Comment toEntity(){
            return Comment.builder()
                    .post(post)
                    .content(content)
                    .writer(member.getNickname())
                    .member(member)
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
        private Long memberId;
        @JsonProperty("isWriter")
        private boolean isWriter;

        public Response(Comment entity){
            this.id = entity.getId();
            this.postId = entity.getPost().getId();
            this.content = entity.getContent();
            this.writer = entity.getWriter();
            this.createdDate = entity.getCreatedDate();
            this.deleteYn = entity.getDeleteYn();
            this.memberId = entity.getMember().getId();
        }

        //댓글 작성자인지 확인
        public void setIsWriter(boolean isWriter) {
            this.isWriter = isWriter;

        }
    }

    public static List<CommentDto.Response> CommentListToDto(List<Comment> comments){
        return comments.stream()
                .map(comment -> new CommentDto.Response(comment)).collect(Collectors.toList());
    }



}