package com.sneakermarket.domain.file;

import com.sneakermarket.domain.post.entity.Post;
import com.sneakermarket.domain.post.entity.PostRepository;
import com.sneakermarket.exception.CustomException;
import com.sneakermarket.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final PostRepository postRepository;


    @Transactional
    public void saveFile(final Long postId, final List<File> files){

        if(CollectionUtils.isEmpty(files)){
            return;
        }

        List<FileDto> dtos = FileDto.entityListToDto(files);
        Post post = postRepository.findById(postId).orElseThrow(()-> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        for(FileDto file : dtos) {
            file.setPost(post);
        }

        List<File> entity = FileDto.toEntityList(dtos);

        fileRepository.saveAll(entity);
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
        return fileRepository.findById(id).orElseThrow(()->new CustomException(ErrorCode.POSTS_NOT_FOUND));
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
