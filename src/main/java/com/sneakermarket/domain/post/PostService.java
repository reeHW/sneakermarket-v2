package com.sneakermarket.domain.post;

import com.sneakermarket.common.dto.SearchDto;
import com.sneakermarket.common.paging.Pagination;
import com.sneakermarket.common.paging.PagingResponse;
import com.sneakermarket.domain.file.File;
import com.sneakermarket.domain.file.FileService;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final FileService fileService;

    /**
     * 게시글 저장
     * @param params
     * @return Generated PK
     */
    @Transactional
    public Long save(final String nickname, final PostDto.EditForm editForm, List<File> uploadFiles) {

        Member member = memberRepository.findByNickname(nickname);
        editForm.setMember(member);

        Post post = editForm.toEntity();
        postRepository.save(post);

        fileService.saveFile(uploadFiles, post);

        return post.getId();
    }

    /**
     * 게시글 수정
     * @param params - 게시글 정보
     * @return PK
     */
    @Transactional
    public Post update(final PostDto.EditForm editForm) {

        Post entity = postRepository.findById(editForm.getId()).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(editForm);
        return entity;
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

        return new PostDto.Response(entity);
    }

    /**
     * 게시글 조회수 증가
     */

    @Transactional
    public void updateView(Long id){
        Post entity = postRepository.findById(id).orElseThrow(() ->new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.increaseViewCnt();
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

