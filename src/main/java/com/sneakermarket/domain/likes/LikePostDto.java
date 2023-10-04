package com.sneakermarket.domain.likes;

import com.sneakermarket.domain.post.PostDto;

public class LikePostDto {

    public static PostDto.Response entityToDto(LikePost likePost){
        PostDto.Response response = PostDto.entityToDto(likePost.getPost());
        return response;
    }
}
