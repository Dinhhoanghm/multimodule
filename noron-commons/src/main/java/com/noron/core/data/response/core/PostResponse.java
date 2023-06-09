package com.noron.core.data.response.core;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class PostResponse {
    private String title;
    private String content;
    private UserResponse userResponse;
    private Integer numComment;
    private String topic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
