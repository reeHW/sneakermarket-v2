package com.sneakermarket.domain.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    PostService postService;

    @Autowired
    PostMapper postMapper;

    @Test
    void save() {
        PostDto.EditForm postDto = PostDto.EditForm.builder()
                .title("1번 게시글 제목")
                .content("1번 게시글 내용")
                .price(200000)
                .size(245)
                .saleStatus(SaleStatus.READY)
                .build();

        Long postId = postService.savePost(postDto);
        Post post = postMapper.findById(postId);

        assertEquals(postDto.getTitle(), post.getTitle());

        List<Post> posts = postMapper.findAll();
        System.out.println("전체 게시글 개수는 : " + posts.size() + "개입니다.");

    }

    @Test
    void findAll() {
        Post post = postMapper.findById(1L);
        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

  /*  @Test
    void update() {
        //1. 게시글 수정
        PostDto postDto  = PostDto.builder()
                .title("1번 게시글 제목 수정합니다.")
                .content("1번 게시글 내용 수정합니다.")
                .saleStatus(SaleStatus.RESERVATION)
                .price(300000)
                .size(245)
                .build();

        Post post = postDto.toEntity(postDto);

        postMapper.update(post);

        //2. 게시글 상세정보 조회
        Post findPost = postMapper.findById(1L);
        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(findPost);
            System.out.println(postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }*/

    @Test
    void delete() {
        System.out.println("삭제 이전의 전체 게시글 개수는 : " + postMapper.findAll().size() + "개입니다");
        postMapper.deleteById(3L);
        System.out.println("삭제 이후의 전체 게시글 개수는 : " + postMapper.findAll().size() + "개입니다");
    }
}
