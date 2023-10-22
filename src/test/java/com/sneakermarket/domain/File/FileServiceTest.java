package com.sneakermarket.domain.File;

import com.sneakermarket.global.common.file.FileUtils;
import com.sneakermarket.domain.file.File;
import com.sneakermarket.domain.file.FileDto;
import com.sneakermarket.domain.file.FileRepository;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import com.sneakermarket.domain.post.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class FileServiceTest {

    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private PostService postService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FileRepository fileRepository;

    private PostDto.WriteForm postDto;
    private MemberDto.Response memberDto;
    @BeforeEach
    public void before(){

        Member member = memberRepository.save(new Member().builder()
                .username("LOCALTEST")
                .password("test123@")
                .nickname("LOCALTEST")
                .build());

        memberDto = new MemberDto.Response(member);

        postDto = PostDto.WriteForm.builder()
                .price(200000)
                .saleStatus(SaleStatus.판매중)
                .content("내용")
                .size(270)
                .title("제목")
                .writer(memberDto.getNickname())
                .build();

    }
    @Test
    public void testFileService() throws IOException {
        //given
        String contentType = "image/png";
        String filePath1 = "src/test/resources/img/testUpload.png";
        String filePath2 = "src/test/resources/img/testUpload2.png";
        MockMultipartFile mockMultipartFile1 = (MockMultipartFile) createMockMultipartFile("testUpload1", contentType, filePath1);
        MockMultipartFile mockMultipartFile2 = (MockMultipartFile) createMockMultipartFile("testUpload2", contentType, filePath2);


        List<MultipartFile> attachmentFiles = new ArrayList<>();
        attachmentFiles.add(mockMultipartFile1);
        attachmentFiles.add(mockMultipartFile2);

        List<FileDto.Attachment> uploadedFiles = new ArrayList<>();

        uploadedFiles.addAll(fileUtils.uploadFiles(attachmentFiles));


        //when
        Long postId = postService.save(memberDto, postDto, uploadedFiles);

        List<File> savedFiles = fileRepository.findAllByPostId(postId);
        System.out.println(savedFiles.get(0).getSaveName());


        //then
        assertThat(savedFiles).hasSameSizeAs(uploadedFiles);
        assertThat(savedFiles.get(0).getSaveName()).isEqualTo(uploadedFiles.get(0).getSaveName());


    }

    private MultipartFile createMockMultipartFile(String originalFileName, String contentType, String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        return new MockMultipartFile("image",originalFileName, contentType, fileInputStream);
    }
}
