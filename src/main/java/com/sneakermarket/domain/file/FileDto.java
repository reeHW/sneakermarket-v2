package com.sneakermarket.domain.file;

import com.sneakermarket.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class FileDto {

    @Getter
    @NoArgsConstructor
    public static class Attachment {
        private Post post;
        private String originalName; //원본 파일명
        private String saveName; // 저장 파일명
        private long size; //파일 크기
        private String filePath; // 파일 경로


        @Builder
        public Attachment(Post post, java.lang.String originalName, java.lang.String saveName, long size, java.lang.String filePath) {
            this.post = post;
            this.originalName = originalName;
            this.saveName = saveName;
            this.size = size;
            this.filePath = filePath;
        }


        public static FileDto.Attachment entityToDto(File entity) {
            return Attachment.builder()
                    .originalName(entity.getOriginalName())
                    .filePath(entity.getFilePath())
                    .saveName(entity.getSaveName())
                    .size(entity.getSize())
                    .build();
        }


        public static List<Attachment> entityListToDto(List<File> files) {
            return files.stream()
                    .map(file -> entityToDto(file)).collect(Collectors.toList());

        }

        public void setPost(Post post){
            this.post = post;
        }

        public File toEntity() {
            return File.builder()
                    .post(post)
                    .filePath(filePath)
                    .originalName(originalName)
                    .saveName(saveName)
                    .size(size)
                    .build();

        }

        public static List<File> toEntityList(List<Attachment> dtos) {
            return dtos.stream()
                    .map(dto -> dto.toEntity())
                    .collect(Collectors.toList());

        }
    }

    @Getter
    @NoArgsConstructor
    public static class Response{
        private Long id;
        private String saveName;
        private Long postId;

    }

    public static FileDto.Response entityToDto(File file){
        FileDto.Response response = new FileDto.Response();
        response.id = file.getId();
        response.saveName = file.getSaveName();
        response.postId = file.getPost().getId();
        return response;

    }


    public static List<FileDto.Response> entityListToDtoList(List<File> fileList) {
        return fileList.stream()
                .map(file -> FileDto.entityToDto(file))
                .collect(Collectors.toList());
    }



}
