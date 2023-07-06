package com.sneakermarket.domain.post;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sneakermarket.domain.post.PostDto.Response.PostListToDto;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    /**
     * 게시글 저장
     * @param editForm
     * @return Generated PK
     */

    @Transactional
    public Long savePost(final PostDto.EditForm editForm) {
        Post post = new Post(
                editForm.getId(),
                editForm.getTitle(),
                editForm.getContent(),
                editForm.getPrice(),
                editForm.getSize(),
                editForm.getSaleStatus()
        );
        postMapper.save(post);
        return post.getId();
    }

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */
    public PostDto.Response findPostById(final Long id) {
        Post post = postMapper.findById(id);
        return new PostDto.Response(post);
    }

    /**
     * 게시글 수정
     * @param editForm - 게시글 정보
     * @return PK
     */
    @Transactional
    public Long updatePost(final PostDto.EditForm editForm){
        Post post = new Post(
                editForm.getId(),
                editForm.getTitle(),
                editForm.getContent(),
                editForm.getPrice(),
                editForm.getSize(),
                editForm.getSaleStatus()
        );
        postMapper.update(post);
        return post.getId();
    }

    /**
     * 게시글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deletePost(final Long id){
        postMapper.deleteById(id);
        return id;
    }

    /**
     * 게시글 리스트 조회
     * @return list
     */
    public List<PostDto.Response> findAllPost(){
        List<Post> post = postMapper.findAll();
        return PostListToDto(post);
    }

}
