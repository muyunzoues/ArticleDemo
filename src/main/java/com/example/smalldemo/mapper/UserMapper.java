package com.example.smalldemo.mapper;

import com.example.smalldemo.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findUserByUserName(String username);

    @Insert("insert into user(username,password,create_time,update_time)" +
            "values (#{param1}, #{param2}, now(), now())")
    void add(String username, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id = #{id}")
    void update(User user);

    @Update("update user set user_pic= #{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(@Param("avatarUrl")String avatarUrl, @Param("id")Integer id);

    @Update("update user set password= #{md5String} where id= #{id}")
    void updatePwd(@Param("md5String")String md5String, @Param("id")Integer id);

}
