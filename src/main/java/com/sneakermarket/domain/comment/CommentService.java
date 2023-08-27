package com.sneakermarket.domain.comment;

import com.sneakermarket.common.paging.Pagination;
import com.sneakermarket.common.paging.PagingResponse;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import com.sneakermarket.domain.post.Post;
import com.sneakermarket.domain.post.PostRepository;
import com.sneakermarket.exception.CustomException;
import com.sneakermarket.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    /**
     *
     * @param postId - post PK
     * @param editForm - 댓글 정보
     * @return
     */
    @Transactional
    public CommentDto.Response saveComment(MemberDto.Response memberDto, final Long postId, final CommentDto.EditForm editForm){
        Member member = memberRepository.findByNickname(memberDto.getNickname());
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        editForm.setMember(member);
        editForm.setPost(post);

        Comment entity = editForm.toEntity();
        commentRepository.save(entity);
        post.getComments().add(entity);

        return new CommentDto.Response(entity);
    }

    /**
     * 댓글 리스트 조회
     * @param params - search condition
     * @return 특정 게시글에 등록된 댓글 리스트
     */

    public PagingResponse<CommentDto.Response> findAllComment(final CommentSearchDto params, MemberDto.Response member){
        int count = commentMapper.count(params);
        if(count < 1){
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);
        List<CommentDto.Response> list = commentMapper.findAll(params);

        // 작성자 본인인지 확인하여, 댓글 작성자면 isWriter = true
        list.forEach(comment -> {
            boolean isWriter = Objects.equals(comment.getMemberId(), member.getId());
            comment.setIsWriter(isWriter);
        });


        return new PagingResponse<>(list, pagination);
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
     * 댓글 수정
     * @param editForm - 댓글 form
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


}