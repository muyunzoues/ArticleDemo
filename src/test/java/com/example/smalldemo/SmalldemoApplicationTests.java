package com.example.smalldemo;

import com.example.smalldemo.pojo.User;
import com.example.smalldemo.service.UserService;
import com.example.smalldemo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SmalldemoApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        String username = "lqz";
        String password = "123456";
        User user = userService.findUserByUserName(username);

        if (user == null) {
            userService.register(username, password);
            System.out.println("ok");
        } else {
            System.out.println("no");
        }
    }

}
