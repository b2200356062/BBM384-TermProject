package com.example.demo.Service;


import com.example.demo.DTO.PostDto;
import com.example.demo.Entity.Post;
import com.example.demo.UserRepo.PostRepository;
import com.example.demo.mappers.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostDto addPost(PostDto postDto){
        Post post =postMapper.map(postDto);
        post=postRepository.save(post);
        return postMapper.map(post);
    }
    public PostDto updatePost(PostDto postDto){
        Post post=postRepository.findById(postDto.getId()).orElseThrow(()->new RuntimeException("post not found"));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        return postMapper.map(post);
    }
    public void deletePost(Long postId){
       postRepository.deleteById(postId);
    }
    public List<PostDto> getAllPosts( ){
        List <PostDto> posts=postRepository.findAll().stream().map(postMapper::map).toList();
        return posts;
    }
    public PostDto likeOrDislike(String action, Long id) {
        Post post = postRepository.findById(id).get();
        switch (action) {
            case "like":
                post.setLikesCount(post.getLikesCount()!=null?post.getLikesCount()+ 1:1);
                return postMapper.map(post);
            case "dislike":
                if(post.getLikesCount()>0)
                post.setLikesCount(post.getLikesCount()!=null?post.getLikesCount()-1:0);
                return postMapper.map(post);
            default:
                return postMapper.map(post);
        }
    }
}
