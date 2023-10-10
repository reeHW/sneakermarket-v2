package com.sneakermarket.domain.likes;

import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import com.sneakermarket.domain.post.Post;
import com.sneakermarket.domain.post.PostDto;
import com.sneakermarket.domain.post.PostRepository;
import com.sneakermarket.global.util.exception.CustomException;
import com.sneakermarket.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class LikePostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LikePostRepository likePostRepository;

    /**
     * 관심 게시물 등록
     * @param memberDto
     * @param postId
     * @return postId
     */
    public int like(MemberDto.Response memberDto, Long postId) {
        if(memberDto == null){
            throw new CustomException(ErrorCode.ONLY_MEMBER);
        }

        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        Member member = memberRepository.findById(memberDto.getId()).orElseThrow(()-> new CustomException(ErrorCode.ONLY_MEMBER));


        //좋아요 중복 방지
        if(likePostRepository.findByMemberIdAndPostId(member.getId(), postId).isPresent()){
            throw new CustomException(ErrorCode.HISTORY_ALREADY_EXISTS);
        }

        LikePost likePost = LikePost.builder()
                .member(member)
                .post(post)
                .build();

        likePostRepository.save(likePost);

        post.addLikeCount(post.getLikeCount());

        return post.getLikeCount();

    }

    /**
     *  관심 게시물 해제
     * @param memberDto
     * @param postId
     */

    public int unlike(MemberDto.Response memberDto, Long postId) {
        if(memberDto == null){
            throw new CustomException(ErrorCode.ONLY_MEMBER);
        }
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POST_ID_NOT_FOUND));
        Member member = memberRepository.findById(memberDto.getId()).orElseThrow(()-> new CustomException(ErrorCode.ONLY_MEMBER));

        likePostRepository.deleteByMemberIdAndPostId(member.getId(), post.getId());
        post.removeLikeCount(post.getLikeCount());

        return post.getLikeCount();

    }

    /**
     * 관심 게시물 리스트 조회
     * @param memberDto
     * @param pageable
     */
    public PageImpl<PostDto.Response> list(MemberDto.Response memberDto, Pageable pageable) {
        if(memberDto == null){
            throw new CustomException(ErrorCode.ONLY_MEMBER);
        }

        Page<LikePost> byMemberId = likePostRepository.findByMemberId(memberDto.getId(), pageable);
        List<PostDto.Response> list = byMemberId.getContent().stream()
                .map(likePost -> LikePostDto.entityToDto(likePost))
                .collect(Collectors.toList());

        PageImpl<PostDto.Response> responses = new PageImpl<>(list, byMemberId.getPageable(), byMemberId.getTotalElements());

        return responses;
    }

    /**
     * 관심 게시물 등록여부 조회
     * @param memberId
     * @param postId
     *
     */
    public boolean isLiked(Long memberId, Long postId){
        LikePost likePost = likePostRepository.findByMemberIdAndPostId(memberId, postId).orElse(null);
        if(likePost == null){
            return false;
        }
        return true;
    }
}
