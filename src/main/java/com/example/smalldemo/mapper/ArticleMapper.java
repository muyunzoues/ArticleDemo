package com.example.smalldemo.mapper;

import com.example.smalldemo.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time,is_delete)" +
            "values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime},#{isDelete})")
    void add(Article article);


    List<Article> list(@Param("userId")Integer userId, @Param("categoryId") Integer categoryId, @Param("state") String state);

    @Select("select * from article where id=#{id}")
    Article findById(Integer id);

    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId} where id=#{id}")
    void update(Article article);

    @Update("update article set is_delete=1 where id = #{id}")
    void delete(Integer id);
}
