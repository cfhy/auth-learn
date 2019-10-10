package com.fxb.oauth2.controller;

import com.fxb.oauth2.entity.User;
import com.fxb.oauth2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author fangxiaobai on 2017/10/11 20:50.
 * @description
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = {"list",""},method = RequestMethod.GET)
    public String list(Model model){
        List<User> users = this.userService.findAll();
        model.addAttribute("users",users);
        return "/user/list";
    }

  
    @RequestMapping(value = "create",method = RequestMethod.GET)
    public String toAdd(Model model,User user){
        model.addAttribute("user",user);
        String op = user.getId()==null?"新增":"修改";
        model.addAttribute("op",op);
        return "user/edit";
    }
    
    @RequestMapping(value = "create",method = RequestMethod.POST)
    public String add(RedirectAttributes redirectAttributes, User user){
        userService.addUser(user);
        redirectAttributes.addFlashAttribute("msg","新增成功");
        return "redirect:/user";
        
    }
    
    @RequestMapping("/{id}/delete")
    public String delete(RedirectAttributes redirectAttributes,@PathVariable User user){
        userService.delete(user);
        redirectAttributes.addFlashAttribute("msg","删除成功");
        return "redirect:/user";
    }

}