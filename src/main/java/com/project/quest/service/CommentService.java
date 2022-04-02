package com.project.quest.service;

import com.project.quest.request.CommentCreateRequest;
import com.project.quest.request.CommentUpdateRequest;
import com.project.quest.model.Comment;
import com.project.quest.model.Post;
import com.project.quest.model.User;
import com.project.quest.repository.CommentRepository;
import com.project.quest.response.CommentResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final  UserService userService;
    private final  PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> comments;
        if (userId.isPresent() && postId.isPresent()) {
            comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            comments =  commentRepository.findByUserId(userId);
        }
        else if (postId.isPresent()) {
            comments = commentRepository.findByPostId(postId);
        }
        else{
            comments = commentRepository.findAll();
        }
        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }

    public Comment getCommentByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUserByUserId(commentCreateRequest.getUserId());
        Post post = postService.getPostByPostId(commentCreateRequest.getPostId());
        if (user != null && post != null){
            Comment commentToSave = new Comment();
            commentToSave.setId(commentCreateRequest.getId());
            commentToSave.setUser(user);
            commentToSave.setPost(post);
            commentToSave.setText(commentCreateRequest.getText());
            commentToSave.setCreateDate(new Date());
            return commentRepository.save(commentToSave);
        }
        else
            return null;
    }

    public Comment updateCustomer(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment commentToUpdate = comment.get();
            commentToUpdate.setText(commentUpdateRequest.getText());
            return commentRepository.save(commentToUpdate);
        } else {
            return null;
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
