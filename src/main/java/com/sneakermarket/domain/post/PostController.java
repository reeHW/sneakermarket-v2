package com.sneakermarket.domain.post;

import com.sneakermarket.common.dto.MessageDto;
import com.sneakermarket.common.dto.SearchDto;
import com.sneakermarket.common.paging.PagingResponse;
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

    //게시글 작성 페이지
    @GetMapping("/post/write.do")
    public String openPostWrite(@RequestParam(value="id", required = false) final Long id, Model model){
       if(id != null){
           PostDto.Response response = postService.findPostById(id);
           model.addAttribute("post", response);
       }
       return "post/write";
    }

    //게시글 리스트 페이지
    @GetMapping("/post/list.do")
    public String openPostList(@ModelAttribute("params") final SearchDto params, Model model){
        PagingResponse<PostDto.Response> response = postService.findAllPost(params);
        model.addAttribute("response", response);
        return "post/list";
    }

    //게시글 상세 페이지
    @GetMapping("/post/view.do")
    public String openPostView(@RequestParam final Long id, Model model){
        PostDto.Response post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "post/view";
    }

    //신규 게시글 생성
    @PostMapping("/post/save.do")
    public String savePost(final PostDto.EditForm editForm, Model model){
        postService.savePost(editForm);
        MessageDto message = new MessageDto("게시글이 저장되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    //기존 게시글 수정
    @PostMapping("/post/update.do")
    public String updatePost(final PostDto.EditForm editForm, Model model) {
        postService.updatePost(editForm);
        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list.do", RequestMethod.GET,null);
        return showMessageAndRedirect(message, model);
    }

    //게시글 삭제
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam final Long id, final SearchDto queryParams, Model model){
        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, queryParamsToMap(queryParams));
        return showMessageAndRedirect(message,model);
    }


}
