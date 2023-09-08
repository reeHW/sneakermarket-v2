package com.sneakermarket.domain.post;

import com.sneakermarket.domain.comment.Comment;
import com.sneakermarket.domain.file.File;
import com.sneakermarket.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String writer;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private int price;
    private int size;
    private char deleteYn = 'N'; // 게시글 삭제 유무. 삭제되면 'Y'
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int viewCnt;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    // 단순히 읽어옴, post의 외래키는 comment가 관리함.
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    // 단순히 읽어옴, post의 외래키는 file이 관리함.
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @Builder
    public Post(Member member, String writer, String title, String content, int price, int size, SaleStatus saleStatus) {
        this.member = member;
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
     * @param writeForm
     */
    public void update(PostDto.WriteForm writeForm) {
        this.title = writeForm.getTitle();
        this.size = writeForm.getSize();
        this.content = writeForm.getContent();
        this.price = writeForm.getPrice();
        this.saleStatus = writeForm.getSaleStatus();
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
        this.deleteYn = 'Y';
    }

}
