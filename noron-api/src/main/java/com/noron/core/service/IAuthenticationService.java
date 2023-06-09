package com.noron.core.service;

import com.noron.core.data.request.core.UserLoginRequest;
import com.noron.core.data.request.core.UserSignInAndUpdateRequest;
import com.noron.core.data.response.core.UserResponse;
import org.springframework.stereotype.Component;


public interface IAuthenticationService {
    public String checkValidUser(UserLoginRequest userLoginRequest);
    public String authenticate(UserLoginRequest userLoginRequest);
    public boolean insertUSer(UserSignInAndUpdateRequest userSignInAndUpdateRequest);
}
