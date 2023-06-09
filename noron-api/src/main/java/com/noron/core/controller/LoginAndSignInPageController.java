package com.noron.core.controller;

import com.jwt.JwtUltis;
import com.noron.core.data.request.core.UserLoginRequest;
import com.noron.core.data.request.core.UserSignInAndUpdateRequest;
import com.noron.core.data.response.core.UserResponse;
import com.noron.core.service.IAuthenticationService;
import com.security.UserLoginInfo;
import jakarta.validation.Valid;
import org.apache.commons.codec.digest.Crypt;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authenticate")
public class LoginAndSignInPageController {
    private IAuthenticationService authenticationService;

    private AuthenticationManager authenticationManager;

    private JwtUltis jwtUltis;
    private PasswordEncoder passwordEncoder;

    public LoginAndSignInPageController(IAuthenticationService authenticationService ,AuthenticationManager authenticationManager
                                          ,JwtUltis jwtUltis, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.authenticationManager=authenticationManager;
        this.jwtUltis = jwtUltis;
        this.passwordEncoder= passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                /*Represents the token for an authentication request or for an authenticated principal
                once the request has been processed
                by the AuthenticationManager.authenticate(Authentication) method.*/
                new UsernamePasswordAuthenticationToken(userLoginRequest.getUserAccount(), userLoginRequest.getPassword()));
        // Tạo 1 authentication với 2 object là principal:account và credential:password;

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //đại khái là thông qua cái này để lấy authentication mà mình vùa tạo
        /*This class provides a series of static methods that delegate to
        an instance of SecurityContextHolderStrategy. The purpose of the
        class is to provide a convenient way to specify the strategy that should
         be used for a given JVM. This is a JVM-wide setting,
        since everything in this class is static to facilitate ease of use in calling code.*/
        String jwt = jwtUltis.generateToken(userLoginRequest.getUserAccount());

        UserLoginInfo userDetails = (UserLoginInfo) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(jwt);
    };
//    public ResponseEntity<String> authenticate(@RequestBody UserLoginRequest userLoginRequest) {
//        String token = authenticationService.authenticate(userLoginRequest);
//        Authentication authentication = authenticationManager.authenticate(
//                /*Represents the token for an authentication request or for an authenticated principal
//                once the request has been processed
//                by the AuthenticationManager.authenticate(Authentication) method.*/
//                new UsernamePasswordAuthenticationToken(userLoginRequest.getUserAccount(), userLoginRequest.getPassword()));
//        // Tạo 1 authentication với 2 object là principal:account và credential:password;
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return ResponseEntity.ok(token);

//    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody @Valid UserSignInAndUpdateRequest userSignInAndUpdateRequest) {
//        String password = BCrypt.hashpw(userSignInAndUpdateRequest.getUserPassword(),BCrypt.gensalt(12));
//        userSignInAndUpdateRequest.setUserPassword(password);
        String password = passwordEncoder.encode(userSignInAndUpdateRequest.getUserPassword());
        userSignInAndUpdateRequest.setUserPassword(password);
        boolean insertUSer = authenticationService.insertUSer(userSignInAndUpdateRequest);
        if(insertUSer){
        return ResponseEntity.ok("account has been registered");
        }
        return ResponseEntity.ok("failed");
    }

}
