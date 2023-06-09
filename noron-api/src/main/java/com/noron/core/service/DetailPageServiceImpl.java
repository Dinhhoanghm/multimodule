package com.noron.core.service;

import com.hm.socialmedia.tables.pojos.Comment;
import com.hm.socialmedia.tables.pojos.Post;
import com.hm.socialmedia.tables.pojos.Topic;
import com.hm.socialmedia.tables.pojos.User;
import com.noron.core.ICommentRepo;
import com.noron.core.IPostRepo;
import com.noron.core.ITopicRepo;
import com.noron.core.IUserRepo;
import com.noron.core.data.mapper.core.*;
import com.noron.core.data.request.core.ShortCommentResquest;
import com.noron.core.data.response.core.PostResponse;
import com.noron.core.data.response.core.ShortCommentResponse;
import com.noron.core.data.response.core.UserResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DetailPageServiceImpl implements IDetailPageService {
    private IPostRepo postRepo;
    private ITopicRepo topicRepo;
    private IUserRepo userRepo;
    private ICommentRepo commentRepo;
    private PostMapperImpl postMapper;
    private UserMapperImpl userMapper;
    private ShortCommentMapperImpl shortCommentMapper;

    public DetailPageServiceImpl(IPostRepo postRepo, ITopicRepo topicRepo, IUserRepo userRepo,
                                 ICommentRepo commentRepo, PostMapperImpl postMapper, UserMapperImpl userMapper,
                                 ShortCommentMapperImpl shortCommentMapper) {
        this.postRepo = postRepo;
        this.topicRepo = topicRepo;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
        this.postMapper = postMapper;
        this.userMapper = userMapper;
        this.shortCommentMapper = shortCommentMapper;
    }

    @Override
    public PostResponse getDetailPostById(Integer id) {
        Post post = postRepo.getPostById(id);
        Topic topic = topicRepo.getTopicById(post.getTopicRelatedId());
        User user = userRepo.getUserById(post.getUserOwnerId());
        UserResponse userResponse = userMapper.toDTO(user);
        PostResponse postResponse = postMapper.toDTO(post, topic,userResponse);
        return postResponse;
    }

    @Override
    public List<ShortCommentResponse> getAllShortCommentResponseByDate(Integer postId) {
        List<Comment> commentList = commentRepo.getAllCommentByDate(postId);
        List<ShortCommentResponse> shortCommentResponseList= new ArrayList<>();
        for (Comment comment : commentList) {
            User user = userRepo.getUserById(comment.getUserOwnerId());
            UserResponse userResponse = userMapper.toDTO(user);
            shortCommentResponseList.add(shortCommentMapper.toDTO(comment, userResponse));
        }
        return shortCommentResponseList;

    }

    @Override
    public List<ShortCommentResponse> getAllShortCommentResponseByCommon(Integer postId) {
        List<Comment> commentList = commentRepo.getAllCommentsByCommon(postId);
        List<ShortCommentResponse> shortCommentResponseList= new ArrayList<>();
        for (Comment comment : commentList) {
            User user = userRepo.getUserById(comment.getUserOwnerId());
            UserResponse userResponse = userMapper.toDTO(user);
            shortCommentResponseList.add(shortCommentMapper.toDTO(comment, userResponse));
        }
        return shortCommentResponseList;
    }

    @Override
    public List<String> getRecommendPost(Integer topicId) {
        List<Post> postList = postRepo.getRandPostByTopic(topicId);
        List<String> postRecommend = new ArrayList<>();
        for(Post post: postList){
            postRecommend.add(post.getTitle());
        }
        return postRecommend;
    }

    @Override
    public ShortCommentResponse insertComment(ShortCommentResquest shortCommentResquest, Integer userOwnerId) {
       Comment comment= shortCommentMapper.toEnity(shortCommentResquest);
       User user = userRepo.getUserById(userOwnerId);
       UserResponse userResponse= userMapper.toDTO(user);
       return shortCommentMapper.toDTO(comment,userResponse);
    }
}
