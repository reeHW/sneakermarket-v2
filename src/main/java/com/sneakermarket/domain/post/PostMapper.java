package com.sneakermarket.domain.post;

import com.sneakermarket.global.common.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface PostMapper {

    /**
     * 게시글 수 조회
     */
    int count(final SearchDto params);

    /**
     * 게시글 리스트 조회
     */
    List<PostDto.Response> findAll(final SearchDto params);

}