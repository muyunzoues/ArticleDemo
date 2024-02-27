package com.example.smalldemo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDateTime;

@Data
public class Category {
    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotEmpty(groups = {Add.class,Update.class})
    private String categoryName;//分类名称
    @NotEmpty(groups = {Add.class,Update.class})
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime updateTime;//更新时间
    private Integer isDelete;

    public interface Add extends Default {

    }
    public interface Update extends Default{

    }
}
