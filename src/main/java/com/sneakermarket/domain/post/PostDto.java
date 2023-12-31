package com.sneakermarket.domain.post;


import com.sneakermarket.domain.file.FileDto;
import com.sneakermarket.domain.member.Member;
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
    public static class WriteForm{

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

        private Member member;

        @Builder
        public WriteForm(Long id, String writer, String title, String content, int price, int size, SaleStatus saleStatus) {
            this.id = id;
            this.writer = writer;
            this.title = title;
            this.content = content;
            this.price = price;
            this.size = size;
            this.saleStatus = saleStatus;

        }

        public Post toEntity(){
            return Post.builder()
                    .writer(writer)
                    .title(title)
                    .content(content)
                    .price(price)
                    .size(size)
                    .saleStatus(saleStatus)
                    .member(member)
                    .build();

        }

    }


    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {
        private Long id;
        private String writer; // member 테이블의 nickname
        private String title;
        private String content;
        private int price;
        private int size;
        private char deleteYn;
        private LocalDateTime createdDate;
        private int viewCnt;
        private int likeCount;
        private String saleStatus;
        private Long memberId;


        public Response(Post entity) {
            this.id = entity.getId();
            this.writer = entity.getMember().getNickname();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.price = entity.getPrice();
            this.size = entity.getSize();
            //this.deleteYn = entity.getDeleteYn();
            this.createdDate = entity.getCreatedDate();
            this.viewCnt = entity.getViewCnt();
            this.likeCount = entity.getLikeCount();
            this.saleStatus = entity.getSaleStatus().getValue();
            this.memberId = entity.getMember().getId();
        }

    }

    public static Response entityToDto(Post post) {
        PostDto.Response response = new Response();
        response.id = post.getId();
        response.writer = post.getMember().getNickname();
        response.title = post.getTitle();
        response.content = post.getContent();
        response.price = post.getPrice();;
        response.size = post.getSize();
        response.createdDate = post.getCreatedDate();
        response.saleStatus = post.getSaleStatus().getValue();
        response.viewCnt = post.getViewCnt();
        response.likeCount = post.getLikeCount();
        response.memberId = post.getMember().getId();

        return response;

    }

    public static List<PostDto.Response> PostListToDto(List<Post> posts){
        return posts.stream()
                .map(post -> new PostDto.Response(post)).collect(Collectors.toList());
    }
}
