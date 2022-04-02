package com.project.quest.service;

import com.project.quest.request.CreateLikeRequest;
import com.project.quest.model.Like;
import com.project.quest.model.Post;
import com.project.quest.model.User;
import com.project.quest.repository.LikeRepository;
import com.project.quest.response.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService, PostService postService1) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService1;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()){
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent()){
            list =  likeRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent()){
            list = likeRepository.findByPostId(postId.get());
        }
        else {
            list = likeRepository.findAll();
        }
        return list.stream().map( like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like getLikeByLikeId(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createLike(CreateLikeRequest createLikeRequest) {
        User user = userService.getUserByUserId(createLikeRequest.getUserId());
        Post post = postService.getPostByPostId(createLikeRequest.getPostId());
        if (user != null && post !=null){
            Like likeToSave = new Like();
            likeToSave.setId(createLikeRequest.getId());
            likeToSave.setUser(user);
            likeToSave.setPost(post);
            return likeRepository.save(likeToSave);
        }
        return null;
    }

    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
