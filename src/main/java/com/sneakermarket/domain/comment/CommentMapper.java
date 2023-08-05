package com.sneakermarket.domain.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public  interface CommentMapper {

    /**
     * 댓글 리스트 조회
     * @param postId - post PK
     * @param params - search conditions
     * @return 댓글 리스트
     */
    List<CommentDto.Response> findAll(CommentSearchDto params);

    /**
     * 댓글 수 카운팅
     * @param params - search conditions
     * @return 댓글 수
     */
    int count(CommentSearchDto params);

}

