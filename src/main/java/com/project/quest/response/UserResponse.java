package com.project.quest.response;

import com.project.quest.model.User;
import lombok.Data;

@Data
public class UserResponse {
    Long id;
    int avatarId;
    String userName;

    public UserResponse(User entity){
        this.id = entity.getId();
        this.avatarId = entity.getAvatar();
        this.userName = entity.getUserName();
    }
}
