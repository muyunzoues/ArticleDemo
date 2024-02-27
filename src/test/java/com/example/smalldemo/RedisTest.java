package com.example.smalldemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest//单元测试启动之前，先初始化spring容器
public class RedisTest {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){

        ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
        operations.set("username","lqz");
        operations.set("id","1",15, TimeUnit.SECONDS);

    }
    @Test
    public void test01(){
        ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
        System.out.println(operations.get("username"));

    }
}
