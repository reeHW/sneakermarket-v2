package com.sneakermarket.domain.file;

import com.sneakermarket.common.file.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileApiController {

    private final FileService fileService;
    private final FileUtils fileUtils;

    // 파일 리스트 조회
    @GetMapping("/posts/{postId}/files")
    public List<FileDto.Response> findAllFileByPostId(@PathVariable final Long postId) {
        List<File> entity = fileService.findAllFileByPostId(postId);
        return FileDto.Response.entityListToDtoList(entity);
    }

    // 이미지 파일 보여주기
    @GetMapping("/posts/{postId}/files/{fileId}/img")
    public ResponseEntity<Resource> displayImg(@PathVariable final long postId, @PathVariable final Long fileId) {
        File file = fileService.findFileById(fileId);

        // 파일이 존재하는지 확인
        if (file != null) {
            Resource resource = fileUtils.readFileAsResource(file);
            String contentType = getContentType(file.getOriginalName());
            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(resource);
        } else {
            // 파일이 존재하지 않으면 404 Not Found 응답
            return ResponseEntity.notFound().build();
        }
    }

    // 파일 확장자에 따라 Content-Type 결정
    private String getContentType(String fileName) {
        String extension = StringUtils.getFilenameExtension(fileName);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            default:
                return "application/octet-stream"; // 기본적으로 binary 형식으로 처리
        }
    }

}