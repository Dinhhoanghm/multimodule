package com.noron.core.service;

import com.noron.core.data.request.core.ShortCommentResquest;
import com.noron.core.data.response.core.PostResponse;
import com.noron.core.data.response.core.ShortCommentResponse;
import org.springframework.stereotype.Component;

import java.util.List;


public interface IDetailPageService {
    public PostResponse getDetailPostById(Integer Id);
    public List<ShortCommentResponse> getAllShortCommentResponseByCommon(Integer postId);
    public List<ShortCommentResponse> getAllShortCommentResponseByDate(Integer postId);

    public List<String> getRecommendPost(Integer topicId);
    public ShortCommentResponse insertComment(ShortCommentResquest shortCommentResquest,Integer userOwnerId);
}
