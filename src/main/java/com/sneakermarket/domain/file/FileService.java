package com.sneakermarket.domain.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileMapper fileMapper;

    @Transactional
    public void saveFile(final Long postId, final List<File> files){
        if(CollectionUtils.isEmpty(files)){
            return;
        }
        for(File file : files){
            file.setPostId(postId);
        }
        fileMapper.saveAll(files);
    }
}
