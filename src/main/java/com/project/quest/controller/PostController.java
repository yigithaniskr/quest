package com.project.quest.controller;

import com.project.quest.request.PostCreateRequest;
import com.project.quest.request.PostUpdateRequest;
import com.project.quest.model.Post;
import com.project.quest.response.PostResponse;
import com.project.quest.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(@RequestParam Optional<Long> userId){
        return ResponseEntity.ok(postService.getAllPosts(userId));
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostCreateRequest newPostCreateRequest){
        return ResponseEntity.ok(postService.createPost(newPostCreateRequest));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostByPostId(@PathVariable  Long postId){
        return ResponseEntity.ok(postService.getPostByPostIdWithLikes(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest){
        return ResponseEntity.ok(postService.updatePost(postId,postUpdateRequest));
    }
    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
