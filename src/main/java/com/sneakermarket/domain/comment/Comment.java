package com.sneakermarket.domain.comment;

import com.sneakermarket.domain.post.entity.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;                       // 댓글 번호 (PK)

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;                   // 게시글 번호 (FK)
    private String content;                // 내용
    private String writer;                 // 작성자
    private Boolean deleteYn = false;      // 삭제 여부
    private LocalDateTime createdDate;     // 생성일시
    private LocalDateTime modifiedDate;    // 최종 수정일시


    @Builder
    public Comment(Post post, String content, String writer, Boolean deleteYn, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.post = post;
        this.content = content;
        this.writer = writer;
        this.deleteYn = deleteYn;

    }

    @PrePersist //최초 Persist될 때 수행
    public void setCreateTime() {
        this.createdDate = LocalDateTime.now();
    }


    /**
     * 댓글 수정
     * @param editForm
     */
    public void update(CommentDto.EditForm editForm){
        this.content = editForm.getContent();
        this.modifiedDate = LocalDateTime.now();
    }


    public void delete(){
        this.deleteYn = true;
    }

}