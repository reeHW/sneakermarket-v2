package com.sneakermarket.domain.post;

import com.sneakermarket.common.dto.SearchDto;
import com.sneakermarket.common.paging.Pagination;
import com.sneakermarket.domain.post.entity.Post;
import com.sneakermarket.domain.post.entity.PostRepository;
import com.sneakermarket.exception.CustomException;
import com.sneakermarket.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(final PostDto.EditForm params) {
        Post post = Post.builder()
                .writer("테스터")
                .title(params.getTitle())
                .saleStatus(params.getSaleStatus())
                .content(params.getContent())
                .price(params.getPrice())
                .size(params.getSize())
                .build();
        postRepository.save(post);
        return post.getId();
    }

    /**
     * 게시글 리스트 조회
     */
    public List<PostDto.Response> findAll() {

        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createdDate");
        List<Post> list = postRepository.findAll(sort);
        return PostDto.PostListToDto(list);
    }

    /**
     * 게시글 리스트 조회 - (With. pagination information)
     */
    public Map<String, Object> findAll(SearchDto params) {

        // 게시글 수 조회
        int count = postMapper.count(params);

        // 등록된 게시글이 없는 경우, 로직 종료
        if (count < 1) {
            return Collections.emptyMap();
        }

        // 페이지네이션 정보 계산
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        // 게시글 리스트 조회
        List<PostDto.Response> list = postMapper.findAll(params);

        // 데이터 반환
        Map<String, Object> response = new HashMap<>();
        response.put("params", params);
        response.put("list", list);
        return response;
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(final Long id, final PostDto.EditForm params) {

        Post entity = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params);
        return id;
    }

//    /**
//     * 게시글 저장
//     * @param editForm
//     * @return Generated PK
//     */
//
//    @Transactional
//    public Long savePost(final HttpSession session, final PostDto.EditForm editForm) {
//
//        MemberDto.FindForm loginMember = (MemberDto.FindForm) session.getAttribute(SessionConstants.LOGIN_MEMBER);
//
//        Post post = Post.builder()
//                .writer(loginMember.getNickname())
//                //.userId(loginMember.getId())
//                .title(editForm.getTitle())
//                .saleStatus(editForm.getSaleStatus())
//                .content(editForm.getContent())
//                .price(editForm.getPrice())
//                .size(editForm.getSize())
//                .build();
//        postRepository.save(post);
//        return post.getId();
//    }
//
//    /**
//     * 게시글 수정
//     * @param editForm - 게시글 정보
//     * @return PK
//     */
//    @Transactional
//    public Long updatePost(final PostDto.EditForm editForm){
//        Post post = postRepository.findById(editForm.getId())
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
//
//        post.update(editForm);
//        return post.getId();
//    }
//
//    /**
//     * 게시글 삭제
//     * @param id - PK
//     * @return PK
//     */
//    @Transactional
//    public Long deletePost(final Long id){
//        postRepository.deleteById(id);
//        return id;
//    }
//
//    /**
//     * 게시글 상세정보 조회
//     * @param id - PK
//     * @return 게시글 상세정보
//     */
//    public PostDto.Response findPostById(final Long id) {
//        Post entity = postRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
//        return new PostDto.Response(entity);
//    }
//
//    /**
//     * 게시글 리스트 조회
//     * @param params - search conditions
//     * @return list & pagination information
//     */
//
//    @Transactional
//    public List<Post> search(String keyword) {
//        List<Post> postList = postRepository.findByTitleContaining(keyword);
//        return postList;
//    }
//    public PagingResponse<PostDto.Response> findAllPost(final SearchDto params){
//
//        // 조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
//        int count = postMapper.count(params);
//        if (count < 1) {
//            return new PagingResponse<>(Collections.emptyList(), null);
//        }
//
//        // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
//        Pagination pagination = new Pagination(count, params);
//        //params.setPagination(pagination);
//
//
//        // 계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
//        List<Post> entity = postMapper.findAll(params);
//        List<PostDto.Response> list = PostListToDto(entity);
//        return new PagingResponse<>(list, pagination);
//
//    }

}

