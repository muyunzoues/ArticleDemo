package com.example.smalldemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.smalldemo.mapper")
public class SmalldemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmalldemoApplication.class, args);
    }

}
