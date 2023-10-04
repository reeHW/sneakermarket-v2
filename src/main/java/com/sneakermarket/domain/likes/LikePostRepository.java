package com.sneakermarket.domain.likes;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    @Modifying
    void deleteByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);

    Page<LikePost> findByMemberId(Long memberId, Pageable pageable);

    Optional<LikePost> findByMemberIdAndPostId(Long memberId, Long postId);
}
