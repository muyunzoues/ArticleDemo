package com.example.smalldemo.interceptors;

import com.example.smalldemo.pojo.Result;
import com.example.smalldemo.utils.JwtUtil;
import com.example.smalldemo.utils.ThreadLocalUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token=request.getHeader("Authorization");
        //验证token
        try {
            //从redis获取相同的token
            ValueOperations<String,String> operations=stringRedisTemplate.opsForValue();
            String redisToken=operations.get(token);
            if(redisToken == null){
                //token以及失效
                throw new RuntimeException();
            }
            Map<String,Object> claims= JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            //拦截器放行
            return true;
        }catch (Exception e){
            response.setStatus(401);
            //不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
