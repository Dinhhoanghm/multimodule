package com.noron.core.controller.model;

import com.noron.core.data.response.core.PostResponse;
import com.noron.core.data.response.core.ShortCommentResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DetailPage {
    private PostResponse postResponse;
    private List<ShortCommentResponse> shortCommentResponseList;
    private List<String> recommendPost;
}
