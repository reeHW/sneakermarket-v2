package com.sneakermarket.domain.file;

import com.sneakermarket.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id; // 파일 번호 (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; // 게시글 번호 (FK)
    private String originalName; //원본 파일명
    private String saveName; // 저장 파일명
    private long size; //파일 크기
    private String filePath; // 파일 경로
    private LocalDateTime createdDate; //생성 일시

    @Builder
    public File(Post post, String originalName, String saveName, Long size, String filePath) {
        this.post = post;
        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;
        this.filePath = filePath;
    }


    @PrePersist //최초 Persist될 때 수행
    public void setCreateTime() {
        this.createdDate = LocalDateTime.now();
    }

}
