package com.example.smalldemo.controller;

import com.auth0.jwt.JWT;
import com.example.smalldemo.pojo.Article;
import com.example.smalldemo.pojo.Category;
import com.example.smalldemo.pojo.PageBean;
import com.example.smalldemo.pojo.Result;
import com.example.smalldemo.service.ArticleService;
import com.example.smalldemo.utils.JwtUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
@CrossOrigin//支持跨域
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated(Article.Add.class) Article article){
        articleService.add( article);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,Integer pageSize,@RequestParam(required = false) Integer categoryId,@RequestParam(required = false) String state){
        PageBean<Article> pb=articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @GetMapping("/detail")
    public Result<Article> detail(Integer id){
        Article c= articleService.findById(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Article article){
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        articleService.delete(id);
        return Result.success();
    }



}
