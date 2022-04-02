package com.project.quest.request;

import lombok.Data;

@Data
public class RefreshRequest {

    Long userId;
    String refreshToken;
}
