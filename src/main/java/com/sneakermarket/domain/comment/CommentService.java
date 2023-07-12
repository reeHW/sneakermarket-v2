package com.sneakermarket.domain.comment;

import com.sneakermarket.common.paging.Pagination;
import com.sneakermarket.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.sneakermarket.domain.comment.CommentDto.CommentListToDto;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    /**
     * 댓글 저장
     * @param editForm - 댓글 정보
     * @return Generated PK
     */
    @Transactional
    public Long saveComment(final CommentDto.EditForm editForm){
        Comment comment = Comment.builder()
                .postId(editForm.getPostId())
                .writer(editForm.getWriter())
                .id(editForm.getId())
                .content(editForm.getContent())
                .build();
        commentMapper.save(comment);
        return comment.getId();
    }

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */

    public CommentDto.Response findCommentById(final Long id){
        Comment comment = commentMapper.findById(id);
        return new CommentDto.Response(comment);
    }

    /**
     * 댓글 수정
     * @param editForm - 댓글 정보
     * @return PK
     */
    @Transactional
    public Long updateComment(final CommentDto.EditForm editForm){
        Comment comment = Comment.builder()
                .postId(editForm.getPostId())
                .writer(editForm.getWriter())
                .id(editForm.getId())
                .content(editForm.getContent())
                .build();
        commentMapper.update(comment);
        return comment.getId();
    }

    /**
     * 댓글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deleteComment(final Long id){
        commentMapper.deleteById(id);
        return id;
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
        List<Comment> entity = commentMapper.findAll(params);
        List<CommentDto.Response> list = CommentListToDto(entity);
        return new PagingResponse<>(list, pagination);
    }
}
