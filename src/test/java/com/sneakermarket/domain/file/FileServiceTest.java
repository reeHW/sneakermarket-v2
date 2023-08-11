/*
package com.sneakermarket.domain.file;

import com.sneakermarket.common.file.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileUtils fileUtils;

    @Test
    public void testSaveFile(){

        //given
        Long postId = 16L;
        List<MultipartFile> files = new ArrayList<>();
        files.add((MultipartFile) File.builder().originalName("file1.txt").saveName("file1_saved.txt").size(1000).filePath("/path/to/file1").build());
        files.add((MultipartFile) File.builder().originalName("file2.txt").saveName("file2_saved.txt").size(2000).filePath("/path/to/file2").build());

        List<File> uploadFile = fileUtils.uploadFiles(files);

        //when
        List<File> saveFiles = fileService.saveFile(postId, uploadFile);

        //then
        assertNotNull(saveFiles);
    }
}
*/
