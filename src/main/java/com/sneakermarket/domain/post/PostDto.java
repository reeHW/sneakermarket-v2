package com.sneakermarket.domain.post;


import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class PostDto {

    @Data
    @NoArgsConstructor
    public static class EditForm{

        private Long id;

        @NotBlank(message = "제목을 입력해주세요")
        private String title;

        @NotBlank(message = "상세 내용을 입력해주세요.")
        private String content;

        @NotNull(message = "값을 입력해주세요.")
        @Min(value = 0, message ="가격을 0원 이상으로 입력해주세요.")
        private Integer price;

        @NotNull(message = "값을 입력해주세요.")
        private Integer size;

        @NotBlank(message = "판매상태를 선택해주세요.")
        private SaleStatus saleStatus;

        @Builder
        public EditForm(Long id, String title, String content, int price, int size, SaleStatus saleStatus) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.price = price;
            this.size = size;
            this.saleStatus = saleStatus;

        }

    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private Long id;
        private String writer; // member 테이블의 email 참조.
        private String title;
        private String content;
        private int price;
        private int size;
        private boolean deleteYn;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        private int viewCnt;
        private SaleStatus saleStatus;

        public Response(Post post){
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.price = post.getPrice();
            this.size = post.getSize();
            this.createdDate = post.getCreatedDate();
            this.viewCnt = post.getViewCnt();
            this.saleStatus = post.getSaleStatus();
        }
        

        public static List<PostDto.Response> PostListToDto(List<Post> posts){
            return posts.stream()
                    .map(post -> new PostDto.Response(post)).collect(Collectors.toList());
        }
    }



}
