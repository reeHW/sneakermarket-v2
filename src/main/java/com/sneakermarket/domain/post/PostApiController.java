package com.sneakermarket.domain.post;

import com.sneakermarket.common.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public Map<String, Object> findAll(final SearchDto params) {
        return postService.findAll(params);
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/posts/{id}")
    public Long save(@PathVariable final Long id, @RequestBody final PostDto.EditForm params){
        return postService.update(id, params);
    }

}
