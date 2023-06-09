package com.noron.core;

import com.hm.socialmedia.tables.pojos.User;
import org.springframework.stereotype.Component;


public interface IUserRepo {
    public User getUserById(Integer id);
    public User findUserByUserAccount(String userAccount);

    public boolean insertUser(User user);
    public User updateUser(User user, Integer id);
    public User deleteUser (Integer id);
}
