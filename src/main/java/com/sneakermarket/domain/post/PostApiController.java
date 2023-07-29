package com.sneakermarket.domain.post;

import com.sneakermarket.common.dto.MessageDto;
import com.sneakermarket.common.dto.SearchDto;
import com.sneakermarket.common.file.FileUtils;
import com.sneakermarket.domain.file.File;
import com.sneakermarket.domain.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;
    private final FileService fileService;
    private final FileUtils fileUtils;

    // 게시글 작성 페이지
    @GetMapping("/{id}")
    public ResponseEntity<PostDto.Response> openPostWrite(@PathVariable("id") Long id) {
        if (id != null) {
            PostDto.Response response = postService.findPostById(id);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {

            return ResponseEntity.ok().build();
        }
    }

    // 게시글 리스트 페이지
    @GetMapping("/list")
    public ResponseEntity openPostList(@ModelAttribute("params") SearchDto params
            /*,@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable*/ ) {

        List<PostDto.ListResponse> posts = postService.list(params);
        //PagingResponse<PostDto.Response> response = postService.findAllPost(params);
        return ResponseEntity.ok(posts);
    }

    // 게시글 상세 페이지
    @GetMapping("/detail/{id}")
    public ResponseEntity<PostDto.Response> openPostView(@PathVariable("id") Long id) {
        PostDto.Response post = postService.findPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 신규 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<MessageDto> savePost(@RequestBody PostDto.EditForm editForm, HttpSession session) {
        Long id = postService.savePost(session, editForm);
        List<File> files = fileUtils.uploadFiles(editForm.getFiles());
        fileService.saveFile(id, files);
        MessageDto message = new MessageDto("게시글이 저장되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return ResponseEntity.ok(message);
    }

    // 기존 게시글 수정
    @PutMapping("/update")
    public ResponseEntity<MessageDto> updatePost(@RequestBody PostDto.EditForm editForm) {
        postService.updatePost(editForm);
        // 파일 업로드
        List<File> uploadFiles = fileUtils.uploadFiles(editForm.getFiles());

        // 파일 정보 저장 (to database)
        fileService.saveFile(editForm.getId(), uploadFiles);

        // 삭제할 파일 정보 조회 (from database)
        List<File> deleteFiles = fileService.findAllFileByIds(editForm.getRemoveFileIds());

        // 파일 삭제 (from disk)
        fileUtils.deleteFiles(deleteFiles);

        // 파일 삭제 (from database)
        fileService.deleteAllFileByIds(editForm.getRemoveFileIds());

        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return ResponseEntity.ok(message);
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDto> deletePost(@PathVariable Long id, @RequestParam SearchDto queryParams) {
        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, queryParamsToMap(queryParams));
        return ResponseEntity.ok(message);
    }

    private Map<String, Object> queryParamsToMap(SearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }


}
