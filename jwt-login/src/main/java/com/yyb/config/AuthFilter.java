package com.yyb.config;

import com.yyb.model.ResData;
import com.yyb.util.BussinessException;
import com.yyb.util.JsonUtil;
import com.yyb.util.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class AuthFilter  extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)){
            responseData(response,new ResData());
            return  false;
        }
        Map<String, Object> retMap = null;
        Claims claims = JwtHelper.parseJWT(token);
        if (claims != null) {
            retMap = new HashMap<>();
            retMap.put("userId", claims.get("userId"));
            retMap.put("userName", claims.get("userName"));
            long expireTime = claims.getExpiration().getTime();
            long currTime= System.currentTimeMillis();
            if(currTime>expireTime){
                responseData(response,new ResData(null,-1,"登录已过期"));
                return false;
            }
        }else {
            responseData(response,new ResData(null,-1,"token不存在"));
            return false;
        }
        return true;
    }

    /**
     * 打印输入错误
     * */
    private void responseData(HttpServletResponse response, ResData data) throws Exception{
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        data.setCode(-1);
        writer.print(JsonUtil.obj2json(data));
        writer.close();
        response.flushBuffer();
    }
}
