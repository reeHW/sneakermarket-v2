package com.sneakermarket.domain.post;

import com.sneakermarket.global.common.dto.SearchDto;
import com.sneakermarket.global.common.paging.PagingResponse;
import com.sneakermarket.domain.file.FileDto;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostServiceTest {


    @Autowired
    private PostService postService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;

    private MemberDto.Response memberDto;
    private List<FileDto.Attachment> uploadFiles =new ArrayList<>();

    @BeforeEach
    void before(){
        Member member = memberRepository.save(new Member().builder()
                .username("test1")
                .password("test123@")
                .nickname("테스터")
                .build());

        memberDto = new MemberDto.Response(member);
        uploadFiles.add(FileDto.Attachment.builder()
                        .originalName("image1")
                        .saveName("image2023")
                        .size(3000L)
                        .filePath("2025image2023")
                .build());
        uploadFiles.add(FileDto.Attachment.builder()
                .originalName("image2")
                .saveName("image22023")
                .size(3000L)
                .filePath("2025image22023")
                .build());

    }


    @Test
    @DisplayName("게시글 저장 테스트")
    void save(){
        //given
        PostDto.WriteForm postDto = PostDto.WriteForm.builder()
                .price(200000)
                .saleStatus(SaleStatus.판매중)
                .content("내용")
                .size(270)
                .title("제목")
                .writer(memberDto.getNickname())
                .build();

        //when
        Long postId = postService.save(memberDto, postDto, uploadFiles);

        //then
        Post post = postRepository.findById(postId).orElse(null);
        assertThat(post.getMember().getNickname()).isEqualTo(memberDto.getNickname());
        assertThat(post.getFiles().size()).isEqualTo(2);

    }

    @Test
    @DisplayName("게시글 상세보기 테스트")
    void view() {
        //given
        PostDto.WriteForm postDto = PostDto.WriteForm.builder()
                .price(200000)
                .saleStatus(SaleStatus.판매중)
                .content("내용")
                .size(270)
                .title("제목")
                .writer(memberDto.getNickname())
                .build();

        //when
        Long postId = postService.save(memberDto, postDto, uploadFiles);
        PostDto.Response postById = postService.findPostById(postId);

        //then
        assertThat(postDto.getTitle()).isEqualTo(postById.getTitle());
        assertThat(postDto.getSize()).isEqualTo(postById.getSize());
        assertThat(postDto.getContent()).isEqualTo(postById.getContent());

    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void update(){
        //given
        PostDto.WriteForm postDto = PostDto.WriteForm.builder()
                .price(200000)
                .saleStatus(SaleStatus.판매중)
                .content("내용")
                .size(270)
                .title("제목")
                .writer(memberDto.getNickname())
                .build();

        Long postId = postService.save(memberDto, postDto, uploadFiles);

        //when
        PostDto.WriteForm updateDto = PostDto.WriteForm.builder()
                .id(postId)
                .price(200000)
                .saleStatus(SaleStatus.예약중)
                .content("내용 수정")
                .size(270)
                .title("제목 수정")
                .writer(memberDto.getNickname())
                .build();

        postService.update(updateDto, uploadFiles);

        //then
        PostDto.Response postById = postService.findPostById(updateDto.getId());
        assertThat(postById.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(postById.getContent()).isEqualTo(updateDto.getContent());
        assertThat(postById.getSaleStatus()).isEqualTo(updateDto.getSaleStatus().getValue());

    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void delete(){
        //given
        PostDto.WriteForm postDto = PostDto.WriteForm.builder()
                .price(200000)
                .saleStatus(SaleStatus.판매중)
                .content("내용")
                .size(270)
                .title("제목")
                .writer(memberDto.getNickname())
                .build();
        Long postId = postService.save(memberDto, postDto, uploadFiles);

        //when
        postService.deletePost(postId);

        //then
        Post post = postRepository.findById(postId).orElse(null);
        assertThat(post.getDeleteYn()).isEqualTo('Y');
    }

    @Test
    @DisplayName("상품 리스트 조회 테스트")
    void list(){
        //given
        PostDto.WriteForm postDto1 = PostDto.WriteForm.builder()
                .price(200000)
                .saleStatus(SaleStatus.판매중)
                .content("내용1")
                .size(270)
                .title("제목1")
                .writer(memberDto.getNickname())
                .build();

        PostDto.WriteForm postDto2 = PostDto.WriteForm.builder()
                .price(200000)
                .saleStatus(SaleStatus.판매중)
                .content("내용2")
                .size(270)
                .title("제목2")
                .writer(memberDto.getNickname())
                .build();

        postService.save(memberDto, postDto1, uploadFiles);
        postService.save(memberDto, postDto2, uploadFiles);

        SearchDto searchDto = new SearchDto();
        searchDto.setSearchType("title");
        searchDto.setKeyword("제목1");

        //when
        PagingResponse<PostDto.Response> response = postService.findAll(searchDto);

        assertThat(response.getList().size()).isEqualTo(1);
        assertThat(response.getList().get(0).getTitle()).isEqualTo(postDto1.getTitle());


    }


    /*페이징 테스트용 게시글 생성*/
/*    @Test
    void saveByForeach() {
        for (int i = 1; i <= 1000; i++) {
            PostDto.WriteForm postDto = new PostDto.WriteForm().builder()
                    .title(i + "번 게시글 제목")
                    .content(i + "번 게시글 내용")
                    .writer(memberDto.getNickname())
                    .size(270)
                    .price(300000)
                    .saleStatus(SaleStatus.판매중)
                    .build();

            postService.save(memberDto, postDto, null);

        }
    }*/

}
