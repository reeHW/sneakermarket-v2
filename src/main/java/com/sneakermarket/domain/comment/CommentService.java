package com.sneakermarket.domain.comment;

import com.sneakermarket.global.common.paging.Pagination;
import com.sneakermarket.global.common.paging.PagingResponse;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import com.sneakermarket.domain.post.Post;
import com.sneakermarket.domain.post.PostRepository;
import com.sneakermarket.global.util.exception.CustomException;
import com.sneakermarket.global.util.exception.ErrorCode;
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
     * 댓글 생성
     * @param postId - post PK
     * @param writeForm - 댓글 정보
     *
     */
    @Transactional
    public CommentDto.Response saveComment(MemberDto.Response memberDto, final Long postId, final CommentDto.WriteForm writeForm){

        if(memberDto == null){
            throw new CustomException(ErrorCode.ONLY_MEMBER);
        }

        Member member = memberRepository.findByNickname(memberDto.getNickname());

        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POST_ID_NOT_FOUND));

        writeForm.setMember(member);
        writeForm.setPost(post);

        Comment entity = writeForm.toEntity();
        commentRepository.save(entity);
        post.getComments().add(entity);

        return new CommentDto.Response(entity);
    }

    /**
     * 댓글 리스트 조회
     * @param params - search condition
     * @return 특정 게시글에 등록된 댓글 리스트
     */

    public PagingResponse<CommentDto.Response> findAllComment(final Long postId, final CommentSearchDto params, MemberDto.Response member){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        int count = commentMapper.count(params);
        if(count < 1){
            return new PagingResponse<>(Collections.emptyList(), null);
        }

        Pagination pagination = new Pagination(count, params);
        List<CommentDto.Response> list = commentMapper.findAll(params);

        if(member != null) {
            // 작성자 본인인지 확인하여, 댓글 작성자면 isWriter = true
            list.forEach(comment -> {
                boolean isWriter = Objects.equals(comment.getMemberId(), member.getId());
                comment.setIsWriter(isWriter);
            });
        }


        return new PagingResponse<>(list, pagination);
    }


    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */

    public CommentDto.Response findCommentById(final Long postId, final Long id){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        Comment entity = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_ID_NOT_FOUND));
        return new CommentDto.Response(entity);
    }



    /**
     * 댓글 수정
     * @param writeForm - 댓글 form
     * @return PK
     */
    @Transactional
    public Long updateComment(final Long postId, final Long id, final CommentDto.WriteForm writeForm){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        Comment entity = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_ID_NOT_FOUND));
        entity.update(writeForm);
        return entity.getId();
    }

    /**
     * 댓글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deleteComment(final Long postId, final Long id){
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        Comment entity = commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_ID_NOT_FOUND));
        entity.delete();
        return id;
    }


}