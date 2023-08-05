package com.sneakermarket.domain.post;


import com.sneakermarket.domain.post.entity.Post;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PostDto {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EditForm{

        private Long id;

        private String writer;

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

        private List<MultipartFile> files = new ArrayList<>();
        private List<Long> removeFileIds = new ArrayList<>(); // 삭제할 첨부파일 id List

        @Builder
        public EditForm(Long id, String writer, String title, String content, int price, int size, SaleStatus saleStatus) {
            this.id = id;
            this.writer = writer;
            this.title = title;
            this.content = content;
            this.price = price;
            this.size = size;
            this.saleStatus = saleStatus;

        }

    }


    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long id;
        private String writer; // member 테이블의 nickname 참조.
        private String title;
        private String content;
        private int price;
        private int size;
        private char deleteYn;
        private LocalDateTime createdDate;
        private int viewCnt;
        private String saleStatus;

        public Response(Post entity) {
            this.id = entity.getId();
            this.writer = entity.getWriter();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.price = entity.getPrice();
            this.size = entity.getSize();
            this.deleteYn = entity.getDeleteYn();
            this.createdDate = entity.getCreatedDate();
            this.viewCnt = entity.getViewCnt();
            this.saleStatus = entity.getSaleStatus().getValue();
        }

    }

    public static List<PostDto.Response> PostListToDto(List<Post> posts){
        return posts.stream()
                .map(post -> new PostDto.Response(post)).collect(Collectors.toList());
    }
}
