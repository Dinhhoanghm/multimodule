package com.noron.core.service;


import com.hm.socialmedia.tables.pojos.User;
import com.jwt.JwtUltis;
import com.noron.core.IUserRepo;
import com.noron.core.data.mapper.core.UserMapperImpl;
import com.noron.core.data.request.core.UserLoginRequest;
import com.noron.core.data.mapper.core.UserMapper;
import com.noron.core.data.request.core.UserSignInAndUpdateRequest;
import com.noron.core.data.response.core.UserResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("com.jwt")
public class AuthenticationServiceImpl implements IAuthenticationService{
    private final IUserRepo userRepo;
    private final JwtUltis jwtUltis;
    private final UserMapperImpl userMapper;
    private final AuthenticationManager authenticationManager;



    public AuthenticationServiceImpl(IUserRepo userRepo, JwtUltis jwtUltis,
                                     UserMapperImpl userMapper,AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.jwtUltis = jwtUltis;
        this.userMapper = userMapper;
        this.authenticationManager=authenticationManager;
    }


    @Override
    public String checkValidUser(UserLoginRequest userLoginRequest) {
        User user = userRepo.findUserByUserAccount(userLoginRequest.getUserAccount());
        boolean isValidPs = false ;
        if (userLoginRequest.getPassword()==user.getPassword()){
            isValidPs = true;
        }
    //    final boolean isValidPs = BCrypt.checkpw(userLoginRequest.getPassword(), user.getPassword());
        if (isValidPs){
            return "No account";
        }
        return userLoginRequest.getUserAccount();
    }

    @Override
    public String authenticate(UserLoginRequest userLoginRequest) {
        String userAccount = checkValidUser(userLoginRequest);
        if(userAccount == "No account"){
            return "No Account";
        }
        return jwtUltis.generateToken(userAccount);
    }

    @Override
    public boolean insertUSer(UserSignInAndUpdateRequest userSignInAndUpdateRequest) {
        User user= userMapper.toEnity(userSignInAndUpdateRequest);
        boolean insertUser = userRepo.insertUser(user);
        return insertUser;
    }
}
