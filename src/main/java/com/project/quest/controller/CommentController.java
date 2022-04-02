package com.project.quest.controller;

import com.project.quest.request.CommentCreateRequest;
import com.project.quest.request.CommentUpdateRequest;
import com.project.quest.model.Comment;
import com.project.quest.response.CommentResponse;
import com.project.quest.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getAllComments(@RequestParam Optional<Long> userId,
                                                                @RequestParam Optional<Long> postId){
        return ResponseEntity.ok(commentService.getAllCommentsWithParam(userId,postId));
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentCreateRequest commentCreateRequest){
        return ResponseEntity.ok(commentService.createComment(commentCreateRequest));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentByCommentId(@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.getCommentByCommentId(commentId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId,
                                                 @RequestBody CommentUpdateRequest commentUpdateRequest){
        return ResponseEntity.ok(commentService.updateCustomer(commentId,commentUpdateRequest));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
