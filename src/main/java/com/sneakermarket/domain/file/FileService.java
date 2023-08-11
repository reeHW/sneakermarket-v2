package com.sneakermarket.domain.file;

import com.sneakermarket.common.file.FileUtils;
import com.sneakermarket.domain.post.Post;
import com.sneakermarket.exception.CustomException;
import com.sneakermarket.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Transactional
    public void saveFile(final List<File> uploadFiles, final Post post){

        if(CollectionUtils.isEmpty(uploadFiles)){
            return;
        }

        // setPost를 위한 dto 변환
        List<FileDto.Attachment> dtos = FileDto.Attachment.entityListToDto(uploadFiles);

        for(FileDto.Attachment dto : dtos) {
            dto.setPost(post);
        }

        List<File> entity = FileDto.Attachment.toEntityList(dtos);

        fileRepository.saveAll(entity);
        post.getFiles().addAll(entity);


    }

    /**
     * 파일 리스트 조회
     * @param postId - 게시글 번호 (FK)
     * @return 파일 리스트
     */
    public List<File> findAllFileByPostId(final Long postId) {
        return fileRepository.findAllByPostId(postId);
    }

    /**
     * 파일 리스트 조회
     * @param ids - PK 리스트
     * @return 파일 리스트
     */
    public List<File> findAllFileByIds(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return fileRepository.findAllById(ids);
    }

    /**
     * 파일 상세정보 조회
     * @param id - PK
     * @return 파일 상세정보
     */
    public File findFileById(final Long id) {
        return fileRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.FILE_NOT_FOUND));
    }

    /**
     * 파일 삭제 (from Database)
     * @param ids - PK 리스트
     */
    @Transactional
    public void deleteAllFileByIds(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        fileRepository.deleteAllById(ids);
    }
}
