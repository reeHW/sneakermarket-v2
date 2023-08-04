package com.sneakermarket.domain.post;

import com.sneakermarket.common.dto.SearchDto;
import com.sneakermarket.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    /**
     * 게시글 생성
     */
    @PostMapping("/posts")
    public Long save(@RequestBody final PostDto.EditForm params){
        return postService.save(params);
    }

    /**
     * 게시글 조회
     */

    @GetMapping("/posts")
    public PagingResponse<PostDto.Response> findAll(final SearchDto params) {
        return postService.findAll(params);
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/posts/{id}")
    public Long save(@PathVariable final Long id, @RequestBody final PostDto.EditForm params){
        return postService.update(params);
    }

}
