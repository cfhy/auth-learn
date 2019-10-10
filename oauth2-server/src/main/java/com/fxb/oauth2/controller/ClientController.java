package com.fxb.oauth2.controller;

import com.fxb.oauth2.entity.Client;
import com.fxb.oauth2.entity.User;
import com.fxb.oauth2.services.ClientService;
import com.fxb.oauth2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

/**
 * @author fangxiaobai on 2017/10/11 20:50.
 * @description
 */
@Controller
@RequestMapping("/client")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @RequestMapping(value = {"list",""},method = RequestMethod.GET)
    public String list(Model model){
        List<Client> clients = this.clientService.findAll();
        model.addAttribute("clients",clients);
        return "/client/list";
    }
    
    @RequestMapping(value = {"save"})
    public String save(Client client){
        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID().toString());
        this.clientService.save(client);
        return "redirect:/client/list";
    }
    
    @RequestMapping(value = {"add"})
    public String add(Client client,Model model){
        model.addAttribute(client);
        return "/client/edit";
    }
    
    @RequestMapping("/{id}/delete")
    public String delete(Client client, RedirectAttributes redirectAttributes){
        clientService.delete(client);
        redirectAttributes.addFlashAttribute("msg","修改成功");
        return "redirect:/client/list";
    }

    
    
    

}