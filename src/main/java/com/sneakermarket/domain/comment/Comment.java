package com.sneakermarket.domain.comment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Comment {

    private Long id;                       // 댓글 번호 (PK)
    private Long postId;                   // 게시글 번호 (FK)
    private String content;                // 내용
    private String writer;                 // 작성자
    private Boolean deleteYn;              // 삭제 여부
    private LocalDateTime createdDate;     // 생성일시
    private LocalDateTime modifiedDate;    // 최종 수정일시


    @Builder
    public Comment(Long id, Long postId, String content, String writer, Long loginUserId) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.writer = writer;

    }
}