package com.sneakermarket.domain.post;

import com.sneakermarket.global.common.dto.SearchDto;
import com.sneakermarket.global.common.paging.Pagination;
import com.sneakermarket.global.common.paging.PagingResponse;
import com.sneakermarket.domain.file.File;
import com.sneakermarket.domain.file.FileDto;
import com.sneakermarket.domain.file.FileService;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import com.sneakermarket.global.util.exception.CustomException;
import com.sneakermarket.global.util.exception.ErrorCode;
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
     * @param memberDto 로그인한 사용자
     * @param writeForm 게시글 정보
     * @return Generated PK
     * 스프링 시큐리티로 회원만 접근 가능
     */
    @Transactional
    public Long save(final MemberDto.Response memberDto, final PostDto.WriteForm writeForm, List<FileDto.Attachment> uploadFiles) {

        Member member = memberRepository.findByNickname(memberDto.getNickname());
        writeForm.setMember(member);

        Post entity = writeForm.toEntity();
        postRepository.save(entity);

        if(uploadFiles != null) {
            fileSave(entity, uploadFiles);
        }
        return entity.getId();
    }


    /**
     * 게시글 수정
     * @param writeForm 게시글 정보
     * @return PK
     */
    @Transactional
    public Post update(final PostDto.WriteForm writeForm, List<FileDto.Attachment> uploadFiles) {

        Post entity = postRepository.findById(writeForm.getId()).orElseThrow(() -> new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        entity.update(writeForm);

        fileSave(entity, uploadFiles);

        return entity;
    }

    /**
     * 게시글 삭제
     *
     * @param id - PK
     */
    @Transactional
    public void deletePost(final Long id){
        Post entity = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        entity.delete();
    }

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     */

    @Transactional
    public PostDto.Response findPostById(final Long id) {
        Post entity = postRepository.findById(id).orElseThrow(() ->new CustomException(ErrorCode.POST_ID_NOT_FOUND));

        return new PostDto.Response(entity);
    }

    /**
     * 게시글 조회수 증가
     */

    @Transactional
    public void updateView(Long id){
        Post entity = postRepository.findById(id).orElseThrow(() ->new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        entity.increaseViewCnt();
    }

    /**
     * 게시글 리스트 조회
     * @param params - 검색조건
     * @return list & 페이징
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

    /**
     * 파일 저장
     * @param post
     * @param uploadFiles
     */
    private void fileSave(Post post, List<FileDto.Attachment> uploadFiles) {
        // 최대 첨부 파일 개수
        int maxFileCount = 5;
        // 현재 포스트에 이미 첨부된 파일 개수
        int currentFileCount = post.getFiles().size();
        // 추가하려는 파일이 최대 허용 파일 개수보다 많으면 예외 처리
        if (currentFileCount + uploadFiles.size() > maxFileCount) {
            throw new RuntimeException("첨부 파일은 최대 " + maxFileCount + "개까지만 허용됩니다.");
        }

        for(FileDto.Attachment uploadFile : uploadFiles) {
            uploadFile.setPost(post);
        }

        List<File> postFile = FileDto.Attachment.toEntityList(uploadFiles);
        post.getFiles().addAll(postFile);
        fileService.saveFile(postFile);

    }

}

