package com.noron.core.service;

import com.noron.core.data.response.core.PostResponse;
import org.springframework.stereotype.Component;

import java.util.List;


public interface IHomePageService {
     public List<PostResponse> getAllPostByCommon();
     public List<PostResponse> getAllPostByNewest();



}
