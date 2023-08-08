package com.sneakermarket.domain.file;

import com.sneakermarket.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class FileDto {

    private Long id;
    private Post post;
    private Long postId;
    private String originalName;
    private String saveName;
    private Long size;
    private String filePath;
    private LocalDateTime createdDate;

    @Builder
    public FileDto(Long id, Post post, Long postId, String originalName, String saveName, Long size, String filePath, LocalDateTime createdDate) {
        this.id = id;
        this.post = post;
        this.postId = postId;
        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;
        this.filePath = filePath;
        this.createdDate = createdDate;
    }

    public static FileDto from(File entity) {
        return FileDto.builder()
                .id(entity.getId())
                .postId(entity.getPost().getId())
                .originalName(entity.getOriginalName())
                .filePath(entity.getFilePath())
                .saveName(entity.getSaveName())
                .size(entity.getSize())
                .createdDate(entity.getCreatedDate())
                .build();
    }

    public File toEntity(){
        return File.builder()
                .post(post)
                .filePath(filePath)
                .originalName(originalName)
                .saveName(saveName)
                .originalName(originalName)
                .size(size)
                .build();

    }


    public static List<FileDto> entityListToDto(List<File> files) {
        return files.stream()
                .map(file -> FileDto.from(file)).collect(Collectors.toList());
        
    }

    public static List<File> toEntityList(List<FileDto> dtos) {
        return dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
    }

}
