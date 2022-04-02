package com.project.quest.response;

import com.project.quest.model.Like;
import lombok.Data;

@Data
public class LikeResponse {
    Long id;
    Long userId;
    Long postId;
    public LikeResponse(Like entity){
        this.id= entity.getId();
        this.userId = entity.getUser().getId();
        this.postId = entity.getPost().getId();
    }
}
