package com.selfstudyboot.config.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfstudyboot.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,Object> map=new HashMap<>();
        String token = request.getHeader("token");
        try {
            JwtUtil.verify(token);
            map.put("msg",403);
            System.out.println(request.getHeader("token"));
            return true;
        } catch (SignatureVerificationException signatureVerificationException) {
            signatureVerificationException.printStackTrace();
            map.put("msg","无效的令牌");
        }catch (TokenExpiredException tokenExpiredException){
            tokenExpiredException.printStackTrace();
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException algorithmMismatchException){
            algorithmMismatchException.printStackTrace();
            map.put("msg","算法错误");
        }catch (NullPointerException nullPointerException){
            map.put("msg","还没有token");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("验证的token有问题");
            map.put("msg","token无效");
        }
        String json =new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
