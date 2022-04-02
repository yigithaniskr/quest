package com.project.quest.service;

import com.project.quest.model.Like;
import com.project.quest.request.PostCreateRequest;
import com.project.quest.request.PostUpdateRequest;
import com.project.quest.model.Post;
import com.project.quest.model.User;
import com.project.quest.repository.PostRepository;
import com.project.quest.response.LikeResponse;
import com.project.quest.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private LikeService likeService;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }
    @Autowired
    public void setLikeService(LikeService likeService){
        this.likeService =  likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()) {
            list = postRepository.findByUserId(userId.get());
        }else
            list = postRepository.findAll();
        return list.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(p.getId()));
            return new PostResponse(p, likes);}).collect(Collectors.toList());
    }

    public Post getPostByPostId(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
    public PostResponse getPostByPostIdWithLikes(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(postId));
        return new PostResponse(post, likes);
    }

    public Post createPost(PostCreateRequest newPostCreateRequest) {
        User user =userService.getUserByUserId(newPostCreateRequest.getUserId());
        if (user == null){
            return null;
        }
        Post toSave =  new Post();
        toSave.setId(newPostCreateRequest.getId());
        toSave.setText(newPostCreateRequest.getText());
        toSave.setTitle(newPostCreateRequest.getTitle());
        toSave.setUser(user);
        toSave.setCreateDate(new Date());
        return postRepository.save(toSave);
    }

    public Post updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()){
            Post toUpdate =post.get();
            toUpdate.setText(postUpdateRequest.getText());
            toUpdate.setText(postUpdateRequest.getText());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
