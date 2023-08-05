package com.sneakermarket.domain.post;

import com.sneakermarket.common.dto.SearchDto;
import com.sneakermarket.common.paging.Pagination;
import com.sneakermarket.common.paging.PagingResponse;
import com.sneakermarket.domain.post.entity.Post;
import com.sneakermarket.domain.post.entity.PostRepository;
import com.sneakermarket.exception.CustomException;
import com.sneakermarket.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    /**
     * 게시글 저장
     * @param params
     * @return Generated PK
     */
    @Transactional
    public Long save(final PostDto.EditForm params) {
        Post entity = Post.builder()
                .writer("테스터")
                .title(params.getTitle())
                .saleStatus(params.getSaleStatus())
                .content(params.getContent())
                .price(params.getPrice())
                .size(params.getSize())
                .build();
        postRepository.save(entity);
        return entity.getId();
    }

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     * @return PK
     */
    @Transactional
    public Long update(final PostDto.EditForm params) {

        Post entity = postRepository.findById(params.getId()).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params);
        return entity.getId();
    }

    /**
     * 게시글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deletePost(final Long id){
        Post entity = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.delete();
        return id;
    }

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */

    @Transactional
    public PostDto.Response findPostById(final Long id) {
        Post entity = postRepository.findById(id).orElseThrow(() ->new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.increaseViewCnt();
        return new PostDto.Response(entity);
    }


    /**
     * 게시글 리스트 조회
     * @param params - search conditions
     * @return list & pagination information
     */

    public PagingResponse<PostDto.Response> findAll(final SearchDto params){

        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = postMapper.count(params);
        if (count < 1) {
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        //params.setPagination(pagination);


        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<PostDto.Response> list = postMapper.findAll(params);

        return new PagingResponse<>(list, pagination);

    }




}

