//package com.sneakermarket.domain.post;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class PostServiceTest {
//
//    @Autowired
//    PostService postService;
//    @Test
//    void saveByForeach() {
//        for (int i = 1; i <= 1000; i++) {
//            PostDto.EditForm postDto = new PostDto.EditForm().builder()
//                    .title(i + "번 게시글 제목")
//                    .content(i + "번 게시글 내용")
//                    .writer("테스터1")
//                    .size(270)
//                    .price(300000)
//                    .saleStatus(SaleStatus.판매중)
//                    .build();
//
//            postService.save("테스터2", postDto, null);
//
//        }
//    }
//
//}
