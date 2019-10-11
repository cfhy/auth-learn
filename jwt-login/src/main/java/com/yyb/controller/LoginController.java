package com.yyb.controller;

import com.yyb.model.ResData;
import com.yyb.util.JwtHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    @ResponseBody
    @PostMapping("/userLogin")
    public ResData userLogin(String username, String password, HttpServletRequest request){
        ResData data=new ResData();
        if(username.equals("yyb") && password.equals("123")){
            data.setCode(0);
        }else {
            data.setCode(0);
            data.setMessage("用户名或密码错误");
        }
        //身份验证成功，发放token
        String token = JwtHelper.generateJWT("1", username);
        data.setData(token);
        return data;
    }
}
