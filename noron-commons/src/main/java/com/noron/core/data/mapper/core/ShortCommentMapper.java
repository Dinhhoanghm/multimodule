package com.noron.core.data.mapper.core;

import com.hm.socialmedia.tables.pojos.Comment;
import com.noron.core.data.response.core.ShortCommentResponse;
import com.noron.core.data.response.core.UserResponse;
import com.noron.core.data.request.core.ShortCommentResquest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" )
public interface ShortCommentMapper {
    @Mapping(ignore = true, target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "postId", target = "postId")
    @Mapping(source = "userOwnerId", target = "userOwnerId")
    @Mapping(ignore = true, target = "postUserId")
    @Mapping(ignore = true, target = "voteCount")
    @Mapping(ignore = true, target = "replyCount")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "deletedAt")
    @Mapping(ignore = true, target = "updatedAt")
    Comment toEnity(ShortCommentResquest shortCommentResquest);

    @Mapping(target = "content", source = "comment.content")
    @Mapping(target = "numVote", source = "comment.voteCount")
    @Mapping(target = "numComment", source = "comment.replyCount")
    @Mapping(target = "userResponse", source = "userResponse")
    @Mapping(source = "comment.createdAt", target = "createdAt")
    @Mapping(source = "comment.deletedAt", target = "deletedAt")
    @Mapping(source = "comment.updatedAt", target = "updatedAt")
    ShortCommentResponse toDTO(Comment comment, UserResponse userResponse);
}