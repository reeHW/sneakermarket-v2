package com.sneakermarket.domain.comment;

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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    /**
     *
     * @param postId - post PK
     * @param editForm - 댓글 정보
     * @return
     */
    @Transactional
    public Long saveComment(final Long postId, final CommentDto.EditForm editForm){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        Comment entity = Comment.builder()
                .post(post)
                .writer("테스터")
                .content(editForm.getContent())
                .build();
        
        commentRepository.save(entity);
        return entity.getId();
    }


    /**
     * 댓글 수정
     * @param editForm - 댓글 정보
     * @return PK
     */
    @Transactional
    public Long updateComment(final Long id, final CommentDto.EditForm editForm){
        Comment entity = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));
        entity.update(editForm);
        return entity.getId();
    }

    /**
     * 댓글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deleteComment(final Long id){
        Comment entity = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));
        entity.delete();
        return id;
    }

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */

    public CommentDto.Response findCommentById(final Long id){
        Comment entity = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));
        return new CommentDto.Response(entity);
    }


    /**
     * 댓글 리스트 조회
     * @param params - search condition
     * @return 특정 게시글에 등록된 댓글 리스트
     */

    public PagingResponse<CommentDto.Response> findAllComment(final CommentSearchDto params){
        int count = commentMapper.count(params);
        if(count < 1){
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);
        List<CommentDto.Response> list = commentMapper.findAll(params);
        return new PagingResponse<>(list, pagination);
    }
}