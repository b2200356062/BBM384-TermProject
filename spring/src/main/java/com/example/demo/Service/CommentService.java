package com.example.demo.Service;


import com.example.demo.DTO.CommentDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Post;
import com.example.demo.UserRepo.CommentRepository;
import com.example.demo.UserRepo.PostRepository;
import com.example.demo.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentDto addComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new RuntimeException("post not found"));
        Comment comment = commentMapper.map(commentDto);
        post.getComments().add(comment);
        comment.setPost(post);
        comment = commentRepository.save(comment);
        return commentMapper.map(comment);
    }

    public CommentDto updateComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getId()).orElseThrow(() -> new RuntimeException("comment not found"));
        comment.setText(commentDto.getText());
        return commentMapper.map(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not foundd"));
        return  post.getComments().stream()
                .sorted(Comparator.comparing(Comment::getId))
                .map(commentMapper::map)
                .toList();
    }

    public CommentDto likeOrDislike(String action, Long id) {
        Comment comment = commentRepository.findById(id).get();
        switch (action) {
            case "like":
                comment.setLikesCount(comment.getLikesCount() + 1);
                return commentMapper.map(comment);
            case "dislike":
                if(comment.getLikesCount()>0)
                comment.setLikesCount(comment.getLikesCount() - 1);
                return commentMapper.map(comment);
            default:
                return commentMapper.map(comment);
        }
    }
}
