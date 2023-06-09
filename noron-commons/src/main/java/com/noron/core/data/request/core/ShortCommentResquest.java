package com.noron.core.data.request.core;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShortCommentResquest {
    private Integer userOwnerId;
    private String content;
    private Integer postId;

}
