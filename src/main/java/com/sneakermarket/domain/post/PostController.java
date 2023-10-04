package com.sneakermarket.domain.post;

import com.sneakermarket.global.common.dto.MessageDto;
import com.sneakermarket.global.common.dto.SearchDto;
import com.sneakermarket.global.common.file.FileUtils;
import com.sneakermarket.global.common.paging.PagingResponse;
import com.sneakermarket.domain.file.File;
import com.sneakermarket.domain.file.FileDto;
import com.sneakermarket.domain.file.FileService;
import com.sneakermarket.domain.likes.LikePostService;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.global.config.auth.LoggedInMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;
    private final LikePostService likePostService;
    private final FileUtils fileUtils;

    //사용자에게 메시지 전달하고, 페이지를 리다이렉트함.
    private String showMessageAndRedirect(final MessageDto params, Model model){
        model.addAttribute("params", params);
        return "common/messageRedirect";
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


    //게시글 리스트 페이지
    @GetMapping("/")
    public String openPostList(@ModelAttribute("params") final SearchDto params, @LoggedInMember MemberDto.Response member, Model model){

        if(member != null){
            model.addAttribute("loggedInMember", member.getNickname());
        }

        PagingResponse<PostDto.Response> response = postService.findAll(params);
        model.addAttribute("response", response);
        return "post/list";
    }

    //게시글 작성 페이지
    @GetMapping("/post/write")
    public String openPostWrite(@RequestParam(value="id", required = false) final Long id,  @LoggedInMember MemberDto.Response member, Model model){
        if(member != null){
            model.addAttribute("loggedInMember", member.getNickname());
        }

        if(id != null){
            PostDto.Response response = postService.findPostById(id);
            model.addAttribute("post", response);
        }
        return "post/write";
    }


    //게시글 상세 페이지
    @GetMapping("/post/view")
    public String openPostView(@RequestParam final Long id, @LoggedInMember MemberDto.Response member, Model model){
        PostDto.Response post = postService.findPostById(id);

        if(member != null){
            model.addAttribute("loggedInMember", member.getNickname());

            /*작성자 본인인지 확인*/
            boolean isWriter = post.getMemberId().equals(member.getId());
            model.addAttribute("writer", isWriter);

            /* 현재 로그인한 유저가 이 게시물을 좋아요 했는지 안 했는지 여부 확인 */
            boolean isLike = likePostService.isLiked(member.getId(), id);
            model.addAttribute("like", isLike);
        }

        postService.updateView(id);
        model.addAttribute("post", post);

        return "post/view";
    }

    //신규 게시글 생성
    @PostMapping("/post/save")
    public String savePost(final PostDto.WriteForm writeForm, @LoggedInMember MemberDto.Response member, Model model){

        if(member != null){
            List<FileDto.Attachment> uploadFiles = fileUtils.uploadFiles(writeForm.getFiles());
            postService.save(member, writeForm, uploadFiles);

        }

        MessageDto message = new MessageDto("게시글이 저장되었습니다.", "/", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    //기존 게시글 수정
    @PostMapping("/post/update")
    public String updatePost(final PostDto.WriteForm writeForm, Model model) {
        // 파일 업로드
        List<FileDto.Attachment> uploadFiles = fileUtils.uploadFiles(writeForm.getFiles());

        Post post = postService.update(writeForm, uploadFiles);

        // 삭제할 파일 정보 조회 (from database)
        List<File> deleteFiles = fileService.findAllFileByIds(writeForm.getRemoveFileIds());

        //파일 삭제 (from disk)
        fileUtils.deleteFiles(deleteFiles);

        //파일 삭제 (from database)
        fileService.deleteAllFileByIds(writeForm.getRemoveFileIds());

        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/view?id=" + post.getId(), RequestMethod.GET,null);
        return showMessageAndRedirect(message, model);
    }

    //게시글 삭제
    @PostMapping("/post/delete")
    public String deletePost(@RequestParam final Long id, final SearchDto queryParams, Model model){
        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/", RequestMethod.GET, queryParamsToMap(queryParams));
        return showMessageAndRedirect(message,model);
    }


}
