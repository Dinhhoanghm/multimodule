package com.noron.core.controller;

import com.noron.core.controller.model.DetailPage;
import com.noron.core.data.request.core.ShortCommentResquest;
import com.noron.core.data.response.core.PostResponse;
import com.noron.core.data.response.core.ShortCommentResponse;
import com.noron.core.service.IDetailPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
@RestController("/homepage/{post}")
public class DetailPageController {
    private IDetailPageService detailPageService;

    public DetailPageController(IDetailPageService detailPageService) {
        this.detailPageService = detailPageService;
    }

    @GetMapping("/{postId}/{topicId}")
    public ResponseEntity<DetailPage> DetailPageByCommon(@PathVariable("postId") Integer postId, @PathVariable("topicId")Integer topicId){
        PostResponse postResponse = detailPageService.getDetailPostById(postId);
        List<ShortCommentResponse> commentResponseList = detailPageService.getAllShortCommentResponseByCommon(postId);
        List<String> recommendedPost = detailPageService.getRecommendPost(topicId);
        DetailPage detailPage = new DetailPage().setPostResponse(postResponse)
                .setRecommendPost(recommendedPost)
                .setShortCommentResponseList(commentResponseList);
        return ResponseEntity.ok(detailPage);
    }
    @GetMapping("/{postId}/{topicId}/newest")
    public ResponseEntity<DetailPage> DetailPageByDate(@PathVariable("postId") Integer postId, @PathVariable("topicId")Integer topicId){
        PostResponse postResponse = detailPageService.getDetailPostById(postId);
        List<ShortCommentResponse> commentResponseList = detailPageService.getAllShortCommentResponseByDate(postId);
        List<String> recommendedPost = detailPageService.getRecommendPost(topicId);
        DetailPage detailPage = new DetailPage().setPostResponse(postResponse)
                .setRecommendPost(recommendedPost)
                .setShortCommentResponseList(commentResponseList);
        return ResponseEntity.ok(detailPage);
    }
    @PostMapping("/{userId}")
    public ResponseEntity<ShortCommentResponse> insertComment(@PathVariable Integer userOwnerId, @RequestBody ShortCommentResquest shortCommentResquest){
        ShortCommentResponse shortCommentResponse= detailPageService.insertComment(shortCommentResquest,userOwnerId);
        return ResponseEntity.ok(shortCommentResponse);
    }

}
