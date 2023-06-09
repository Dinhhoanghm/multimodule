package com.security;

import com.hm.socialmedia.tables.pojos.User;
import com.noron.core.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@ComponentScan("com.noron")
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {
        User user = userRepo.findUserByUserAccount(userAccount);
        if (user==null) {
            throw new UsernameNotFoundException("user not found" + userAccount);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getAuthority());
        authorities.add(simpleGrantedAuthority);
        UserLoginInfo userLoginInfo =new UserLoginInfo(
                user.getAccount(),user.getEmail(),user.getPassword(),
                authorities);
        return userLoginInfo;
    }
}
