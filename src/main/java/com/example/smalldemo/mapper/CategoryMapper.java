package com.example.smalldemo.mapper;

import com.example.smalldemo.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Insert("INSERT INTO category(category_name,category_alias,create_user,create_time,update_time,is_delete) " +
            "values (#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime},#{isDelete})")
    void add(Category category);

    @Select("select * from category where create_user= #{userId} and is_delete=0")
    List<Category> list(@Param("userId")Integer userId);

    @Select("select * from category where id =#{id} ")
    Category findById(Integer id);

    @Update("update category set category_name= #{categoryName},category_alias=#{categoryAlias},update_time= #{updateTime} where id = #{id}")
    void update(Category category);

    @Delete("update category set is_delete=1 where id = #{id}")
    void delete(Integer id);
}
