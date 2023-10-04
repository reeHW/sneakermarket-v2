package com.sneakermarket.domain.comment;

import com.sneakermarket.global.common.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentSearchDto extends SearchDto {
    private Long postId; // 게시글 번호 (FK)
}
