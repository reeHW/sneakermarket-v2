package com.sneakermarket.domain.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;

/*    @Test
    public void testSaveFile(){

        //given
        Long postId = 1L;
        List<File> files = new ArrayList<>();
        files.add(File.builder().originalName("file1.txt").saveName("file1_saved.txt").size(1000).filePath("/path/to/file1").build());
        files.add(File.builder().originalName("file2.txt").saveName("file2_saved.txt").size(2000).filePath("/path/to/file2").build());

        //when
        List<File> saveFiles = fileService.saveFile(postId, files);

        //then
        assertNotNull(saveFiles);
    }*/
}
