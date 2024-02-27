package com.example.smalldemo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTtest {

    @Test
    public void test(){
        Map<String,Object> cla=new HashMap<>();
        cla.put("id",1);
        cla.put("username","lqz");
        //生成jwt的代码
        String token= JWT.create()
                .withClaim("user",cla)//生成载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//添加过期时间
                .sign(Algorithm.HMAC256("lqz"));//配置密钥

        System.out.println(token);
    }

    @Test
    public void test01(){
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImxxeiJ9LCJleHAiOjE3MDgxMjg4Mjh9.NHv6lkp0VU-G7N_0GLSDB87ilssj746S8rV9vOwcILM";
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256("lqz")).build();

        DecodedJWT decodedJWT= jwtVerifier.verify(token);//验证token，生成解析后的对象
        Map<String, Claim> cla=decodedJWT.getClaims();
        System.out.println(cla.get("user"));

    }
}
