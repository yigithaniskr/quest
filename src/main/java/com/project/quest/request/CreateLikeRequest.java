package com.project.quest.request;

import lombok.Data;

@Data
public class CreateLikeRequest {
    Long id;
    Long userId;
    Long postId;
}
