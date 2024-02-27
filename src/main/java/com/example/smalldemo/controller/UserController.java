package com.example.smalldemo.controller;


import com.example.smalldemo.pojo.PasswordData;
import com.example.smalldemo.pojo.Result;
import com.example.smalldemo.pojo.User;
import com.example.smalldemo.service.UserService;
import com.example.smalldemo.utils.JwtUtil;
import com.example.smalldemo.utils.Md5Util;
import com.example.smalldemo.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{3,16}$") String username, @Pattern(regexp = "^\\S{3,16}$")String password) {

        User user = userService.findUserByUserName(username);
        if (user == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{3,16}$") String username, @Pattern(regexp = "^\\S{3,16}$")String password){
        User user= userService.findUserByUserName(username);
        if(user == null){
            return Result.error("用户名错误");
        }
        if(Md5Util.getMD5String(password).equals(user.getPassword())){
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token= JwtUtil.genToken(claims);
            //把token存储于redis
            ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
//        Map<String,Object> map=JwtUtil.parseToken(token);
//        String username= (String) map.get("username");
        Map<String,Object> map= ThreadLocalUtil.get();
        String username= (String) map.get("username");
        User user=userService.findUserByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody PasswordData passwordData, @RequestHeader("Authorization") String token){
        String oldPwd=passwordData.getOldPwd();
        String newPwd=passwordData.getNewPwd();
        String rePwd= passwordData.getRePwd();
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少参数");
        }
        Map<String,Object> map=ThreadLocalUtil.get();
        String username= (String) map.get("username");
        User LoginUser=userService.findUserByUserName(username);
        if(!LoginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码不正确");
        }
        if(!rePwd.equals(newPwd)){
            return Result.error("两次密码不一致");
        }
        userService.updatePwd(newPwd);
        //删除redis对应token
        ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
