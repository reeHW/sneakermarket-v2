package com.sneakermarket.domain.File;

import com.sneakermarket.common.file.FileUtils;
import com.sneakermarket.domain.file.File;
import com.sneakermarket.domain.file.FileRepository;
import com.sneakermarket.domain.file.FileService;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberRepository;
import com.sneakermarket.domain.post.Post;
import com.sneakermarket.domain.post.PostRepository;
import com.sneakermarket.domain.post.SaleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class FileIntegrationTest {

    @Autowired
    private FileService fileService;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FileRepository fileRepository;

    private List<File> uploadedFiles = new ArrayList<>();
    private Post post = new Post();



    @BeforeEach
    public void before(){

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

    }
    @Test
    public void testFileService() {
        //given
        byte[] imageBytes1 = new byte[] {};
        byte[] imageBytes2 = new byte[] {};

        MultipartFile multipartFile1 = createMockMultipartFile("image1.png", imageBytes1);
        MultipartFile multipartFile2 = createMockMultipartFile("image2.png", imageBytes2);


        uploadedFiles.addAll(fileUtils.uploadFiles(List.of(multipartFile1, multipartFile2)));

        System.out.println("testFileService: uploadedFiles size = " + uploadedFiles.size());

        //when
        fileService.saveFile(uploadedFiles, post);
        List<File> savedFiles = fileRepository.findAllByPostId(post.getId());

        //then
        assertThat(savedFiles).hasSameSizeAs(uploadedFiles);
        //assertThat(savedFiles.get(0).getSaveName()).isEqualTo(uploadedFiles.get(0).getSaveName());

    }

    private MultipartFile createMockMultipartFile(String originalFileName, byte[] content) {
        return new MockMultipartFile("file", originalFileName, "image/png", content);
    }
}
