package com.sneakermarket.domain.file;

import com.sneakermarket.global.util.exception.CustomException;
import com.sneakermarket.global.util.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static com.sneakermarket.domain.file.FileDto.Response.entityListToDtoList;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;


    public void saveFile(List<File> file) {
        fileRepository.saveAll(file);
    }


    /**
     * 파일 리스트 조회
     * @param postId - 게시글 번호 (FK)
     * @return 파일 리스트
     */
    public List<FileDto.Response> findAllFileByPostId(final Long postId) {
        List<File> entity = fileRepository.findAllByPostId(postId);
        return entityListToDtoList(entity);
    }

    /**
     * 파일 리스트 조회
     * @param ids - PK 리스트
     * @return 파일 리스트
     */

    @Transactional
    public List<File> findAllFileByIds(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return fileRepository.findAllById(ids);

        //entityListToDtoList(entity);
    }

    /**
     * 파일 상세정보 조회
     * @param id - PK
     * @return 파일 상세정보
     */
    public File findFileById(final Long id) {
        return fileRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.FILE_ID_NOT_FOUND));
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
