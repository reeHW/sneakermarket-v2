package com.sneakermarket.domain.post.entity;

import com.sneakermarket.domain.comment.Comment;
import com.sneakermarket.domain.post.PostDto;
import com.sneakermarket.domain.post.SaleStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

//    @JoinColumn(name = "user_id")
//    private Long userId;
    private String writer;
    private String title;
    private String content;
    private int price;
    private int size;
    private boolean deleteYn = false; // 게시글 삭제 유무. 삭제되면 true.
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int viewCnt;
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

   /* @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<PostFile> postFiles = new ArrayList<>();
    //files는 단순히 파일을 가져오기만 할 것이다. item의 외래키는 file이 관리할 것이다. cascade로 item이 삭제될 때 함께 삭제되도록 한다.
*/
    @Builder
    public Post(String writer, String title, String content, int price, int size, SaleStatus saleStatus) {
//        this.userId = userId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.price = price;
        this.size = size;
        this.saleStatus = saleStatus;
    }

    @PrePersist //최초 Persist될 때 수행
    public void setCreateTime() {
        this.createdDate = LocalDateTime.now();
    }


    /**
     * 게시글 수정
     * @param editForm
     */
    public void update(PostDto.EditForm editForm) {
        this.title = editForm.getTitle();
        this.size = editForm.getSize();
        this.content = editForm.getContent();
        this.price = editForm.getPrice();
        this.saleStatus = editForm.getSaleStatus();
        this.modifiedDate = LocalDateTime.now();
    }

    /**
     * 조회수 증가
     */

    public void increaseViewCnt(){
        this.viewCnt++;
    }


    /**
     * 게시글 삭제
     */
    public void delete(){
        this.deleteYn = true;
    }

}
