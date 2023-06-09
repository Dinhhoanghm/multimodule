package com.noron.core.service;


import com.hm.socialmedia.tables.pojos.Post;
import com.hm.socialmedia.tables.pojos.Topic;
import com.hm.socialmedia.tables.pojos.User;
import com.noron.core.ICommentRepo;
import com.noron.core.IPostRepo;
import com.noron.core.ITopicRepo;
import com.noron.core.IUserRepo;
import com.noron.core.data.mapper.core.PostMapper;
import com.noron.core.data.mapper.core.PostMapperImpl;
import com.noron.core.data.mapper.core.UserMapper;
import com.noron.core.data.mapper.core.UserMapperImpl;
import com.noron.core.data.response.core.PostResponse;
import com.noron.core.data.response.core.UserResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class HomePageServiceImpl implements IHomePageService {
    private IPostRepo postRepo;
    private IUserRepo userRepo;
    private ITopicRepo iTopicRepo;
    private ICommentRepo commentRepo;
    private UserMapperImpl userMapper;
    private PostMapperImpl postMapper;


    public HomePageServiceImpl(IPostRepo postRepo, IUserRepo userRepo,
                               ITopicRepo iTopicRepo, ICommentRepo commentRepo,
                               UserMapperImpl userMapper, PostMapperImpl postMapper) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.iTopicRepo = iTopicRepo;
        this.commentRepo = commentRepo;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
    }

    @Override
    public List<PostResponse> getAllPostByCommon() {
        List<Post> postList = postRepo.getAllPostsByCommon();
        List<PostResponse> postResponseList = new ArrayList<>();
        for (Post post : postList) {
            User user = userRepo.getUserById(post.getUserOwnerId());
            UserResponse userResponse = userMapper.toDTO(user);
            Topic topic = iTopicRepo.getTopicById(post.getTopicRelatedId());
            postResponseList.add(postMapper.toDTO(post, topic, userResponse));
        }
        return postResponseList;
    }

    @Override
    public List<PostResponse> getAllPostByNewest() {
        List<Post> postList = postRepo.getAllPostByDate();
        List<PostResponse> postResponseList = new ArrayList<>();
        for (Post post : postList) {
            User user = userRepo.getUserById(post.getUserOwnerId());
            UserResponse userResponse = userMapper.toDTO(user);
            Topic topic = iTopicRepo.getTopicById(post.getTopicRelatedId());
            postResponseList.add(postMapper.toDTO(post, topic, userResponse));
        }
        return postResponseList;
    }
}
