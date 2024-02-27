package com.example.smalldemo.service;

import com.example.smalldemo.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User findUserByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);

}
