package com.sneakermarket.domain.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class File {
    private Long id; // 파일 번호 (PK)
    private Long postId; // 게시글 번호 (FK)
    private String originalName; //원본 파일명
    private String saveName; // 저장 파일명
    private long size; //파일 크기
    private String filePath; // 파일 경로
    private Boolean deleteYn; //삭제 여부
    private LocalDateTime createdDate; //생성 일시
    private LocalDateTime deletedDate; // 삭제 일시

    @Builder
    public File(String originalName, String saveName, long size, String filePath) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;
        this.filePath = filePath;
    }

    public void setPostId(Long postId){
        this.postId = postId;
    }
}
