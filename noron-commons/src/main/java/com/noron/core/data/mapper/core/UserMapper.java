package com.noron.core.data.mapper.core;

import com.hm.socialmedia.tables.pojos.User;
import com.noron.core.data.request.core.UserSignInAndUpdateRequest;
import com.noron.core.data.response.core.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "name", target = "userName")
    @Mapping(source = "account", target = "userAccount")
    @Mapping(source = "email", target = "userEmail")
    @Mapping(source = "avatar", target = "userAvatar")
    UserResponse toDTO(User user);

    @Mapping(target = "name", source = "userName")
    @Mapping(target = "account", source = "userAccount")
    @Mapping(target = "email", source = "userEmail")
    @Mapping(target = "avatar", source = "userAvatar")
    @Mapping(target = "password", source = "userPassword")
    @Mapping(target = "authority", source = "authority")
    User toEnity(UserSignInAndUpdateRequest userSignInAndUpdateRequest);
}