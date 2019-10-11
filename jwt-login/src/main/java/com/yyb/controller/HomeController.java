package com.yyb.controller;

import com.yyb.model.ResData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    @ResponseBody
    @GetMapping("/userinfo")
    public ResData userinfo(){
        Map<String,String> map=new HashMap<String,String>(2);
        map.put("userId","1");
        map.put("userName","yyb");
        return new ResData(map,0,"");
    }
}
