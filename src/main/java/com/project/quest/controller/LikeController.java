package com.project.quest.controller;

import com.project.quest.request.CreateLikeRequest;
import com.project.quest.model.Like;
import com.project.quest.response.LikeResponse;
import com.project.quest.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public ResponseEntity<List<LikeResponse>> getAllLikes(@RequestParam Optional<Long> userId,
                                                          @RequestParam Optional<Long> postId){
        return ResponseEntity.ok(likeService.getAllLikesWithParam(userId,postId));
    }
    @GetMapping("/{likeId}")
    public ResponseEntity<Like> getLikeByLikeId(@PathVariable Long likeId){
        return ResponseEntity.ok(likeService.getLikeByLikeId(likeId));
    }

    @PostMapping
    public ResponseEntity<Like> createLike(@RequestBody CreateLikeRequest createLikeRequest){
        return ResponseEntity.ok(likeService.createLike(createLikeRequest));
    }
    @DeleteMapping("/{likeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long likeId){
        likeService.deleteLike(likeId);
        return ResponseEntity.ok().build();
    }
}
