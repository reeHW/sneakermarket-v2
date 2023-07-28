package com.sneakermarket.domain.post;

import com.sneakermarket.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Post {

    private Long id;
    private Long userId;
    private String writer;
    private String title;
    private String content;
    private int price;
    private int size;
    private boolean deleteYn;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int viewCnt;
    private SaleStatus saleStatus;

    @Builder
    public Post(Long id, Long userId, String writer, String title, String content, int price, int size, SaleStatus saleStatus) {
        this.id = id;
        this.userId = userId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.price = price;
        this.size = size;
        this.saleStatus = saleStatus;
    }

}
