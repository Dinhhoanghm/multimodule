package com.noron.core.controller;

import com.noron.core.controller.model.HomePage;
import com.noron.core.data.response.core.PostResponse;
import com.noron.core.service.IHomePageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/homepage")
public class HomePageController {
    private IHomePageService homePageService;

    public HomePageController(IHomePageService homePageService) {
        this.homePageService = homePageService;
    }

    @GetMapping("/common")
    public ResponseEntity<HomePage> HomePageCommon() {
        List<PostResponse> postResponseList = homePageService.getAllPostByCommon();
        HomePage homePage = new HomePage().setPostResponseList(postResponseList);
        return ResponseEntity.ok(homePage);
    }

    @GetMapping("/new")
    public ResponseEntity<HomePage> HomePageNew() {
        List<PostResponse> postResponseList = homePageService.getAllPostByNewest();
        HomePage homePage = new HomePage().setPostResponseList(postResponseList);
        return ResponseEntity.ok(homePage);
    }
}
