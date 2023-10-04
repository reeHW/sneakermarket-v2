package com.sneakermarket.domain.comment;

import com.sneakermarket.global.common.paging.PagingResponse;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import com.sneakermarket.domain.post.Post;
import com.sneakermarket.domain.post.PostRepository;
import com.sneakermarket.domain.post.SaleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    private MemberDto.Response memberDto;
    private Post post;
    private CommentSearchDto commentSearchDto;


    @BeforeEach
    void before(){
        Member member = memberRepository.save(new Member().builder()
                .username("test1")
                .password("test123@")
                .nickname("테스터")
                .build());
        post = postRepository.save(new Post().builder()
                .title("제목")
                .member(member)
                .writer(member.getNickname())
                .size(270)
                .content("내용")
                .price(200000)
                .saleStatus(SaleStatus.판매중)
                .build());

        memberDto = new MemberDto.Response(member);

        commentSearchDto = new CommentSearchDto(post.getId());
    }

    @Test
    @DisplayName("댓글 작성 테스트")
    void create(){
        //given
        CommentDto.WriteForm dto = new CommentDto.WriteForm("댓글 테스트");

        //when
        CommentDto.Response savedComment = commentService.saveComment(memberDto, post.getId(), dto);

        //then
        Comment comment = commentRepository.findById(savedComment.getId()).orElse(null);
        assertThat(comment.getContent()).isEqualTo(savedComment.getContent());
        assertThat(post.getComments().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 리스트 조회 테스트")
    void list(){
        //given
        CommentDto.WriteForm dto1 = new CommentDto.WriteForm("댓글내용1");
        CommentDto.WriteForm dto2 = new CommentDto.WriteForm("댓글내용2");
        commentService.saveComment(memberDto, post.getId(), dto1);
        commentService.saveComment(memberDto, post.getId(), dto2);


        //when
        PagingResponse<CommentDto.Response> commentList = commentService.findAllComment(post.getId(),commentSearchDto, memberDto);

        //then
        assertThat(commentList.getList().size()).isEqualTo(post.getComments().size());
        assertThat(commentList.getList().get(1).getContent()).isEqualTo(dto1.getContent());
        assertThat(commentList.getList().get(0).getContent()).isEqualTo(dto2.getContent());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void update(){
        //given
        CommentDto.WriteForm dto = new CommentDto.WriteForm("댓글 테스트");
        CommentDto.Response savedComment = commentService.saveComment(memberDto, post.getId(), dto);

        //when
        CommentDto.WriteForm updateDto = new CommentDto.WriteForm("댓글 테스트 수정");
        commentService.updateComment(post.getId(), savedComment.getId(), updateDto);

        //then
        Comment comment = commentRepository.findById(savedComment.getId()).orElse(null);
        Post post = postRepository.findById(this.post.getId()).orElse(null);

        assertThat(updateDto.getContent()).isEqualTo(comment.getContent());
        assertThat(updateDto.getContent()).isEqualTo(post.getComments().get(0).getContent());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void delete(){
        //given
        CommentDto.WriteForm dto = new CommentDto.WriteForm("댓글 테스트");
        CommentDto.Response savedComment = commentService.saveComment(memberDto, post.getId(), dto);

        //when
        commentService.deleteComment(post.getId(), savedComment.getId());

        //then
        Comment comment = commentRepository.findById(savedComment.getId()).orElse(null);
        assertThat(comment.getDeleteYn()).isEqualTo('Y');

    }


    /*페이징 테스트용 댓글 생성*/
/*    @Test
    void saveByForeach() {
        for (int i = 1; i <= 100; i++) {
            CommentDto.EditForm commentDto = new CommentDto.EditForm();
            String nickname = "낙타";

            commentDto.setContent("테스트" + i);

            commentService.saveComment(nickname, 1028L, commentDto);

        }
    }*/

}
