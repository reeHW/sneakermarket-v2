package com.sneakermarket.domain.file;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    /**
     * 파일 정보 저장
     * @param files - 파일 정보 리스트
     */

    void saveAll(List<File> files);

    /**
     * 파일 리스트 조회
     * @param postId - 게시글 번호 (FK)
     * @return 파일 리스트
     */
    List<File> findAllByPostId(Long postId);

    /**
     * 파일 리스트 조회
     * @param ids - PK 리스트
     * @return 파일 리스트
     */
    List<File> findAllByIds(List<Long> ids);

    /**
     * 파일 삭제
     * @param ids - PK 리스트
     */
    void deleteAllByIds(List<Long> ids);

    /**
     * 파일 상세정보 조회
     * @param id - PK
     * @return 파일 상세정보
     */
    File findById(Long id);
}
