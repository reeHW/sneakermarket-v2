package com.sneakermarket.domain.post;

import com.sneakermarket.global.common.dto.SearchDto;
import com.sneakermarket.global.common.file.FileUtils;
import com.sneakermarket.global.config.auth.LoggedInMember;
import com.sneakermarket.domain.file.File;
import com.sneakermarket.domain.file.FileDto;
import com.sneakermarket.domain.file.FileService;
import com.sneakermarket.domain.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final FileService fileService;
    private final FileUtils fileUtils;


    private Map<String, Object> queryParamsToMap(SearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }

/*
    @GetMapping("/posts")
    public ResponseEntity findAll(final SearchDto params) {
        Map map = postService.findAll(params);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
*/



    /**
     * 신규 게시글 생성
     * @param writeForm
     * @param member
     *
     */
    @PostMapping("/posts/")
    public ResponseEntity save(@RequestBody final PostDto.WriteForm writeForm, @LoggedInMember MemberDto.Response member){
            List<FileDto.Attachment> uploadFiles = fileUtils.uploadFiles(writeForm.getFiles());
            Long postId = postService.save(member, writeForm, uploadFiles);

            return ResponseEntity.status(HttpStatus.OK).body(postId);
    }

    /**
     * 기존 게시글 수정
     * @param id
     * @param writeForm
     *
     */
    @PostMapping("/posts/{id}")
    public ResponseEntity update(@PathVariable final Long id, @RequestBody final PostDto.WriteForm writeForm) {
        // 파일 업로드
        List<FileDto.Attachment> uploadFiles = fileUtils.uploadFiles(writeForm.getFiles());

        postService.update(writeForm, uploadFiles);

        // 삭제할 파일 정보 조회 (from database)
        List<File> deleteFiles = fileService.findAllFileByIds(writeForm.getRemoveFileIds());

        //파일 삭제 (from disk)
        fileUtils.deleteFiles(deleteFiles);

        //파일 삭제 (from database)
        fileService.deleteAllFileByIds(writeForm.getRemoveFileIds());

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 게시글 삭제
     * @param id
     * @param queryParams
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity delete(@PathVariable Long id, final SearchDto queryParams){
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body(queryParamsToMap(queryParams));
    }



}
