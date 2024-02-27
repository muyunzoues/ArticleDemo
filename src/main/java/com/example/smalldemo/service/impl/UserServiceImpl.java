package com.example.smalldemo.service.impl;

import com.example.smalldemo.mapper.UserMapper;
import com.example.smalldemo.pojo.User;
import com.example.smalldemo.service.UserService;
import com.example.smalldemo.utils.Md5Util;
import com.example.smalldemo.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findUserByUserName(String username) {
        System.out.println("userMapper: " + userMapper);  // 添加这行日志
        User user=userMapper.findUserByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String= Md5Util.getMD5String(password);

        userMapper.add(username,md5String);

    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }
}
