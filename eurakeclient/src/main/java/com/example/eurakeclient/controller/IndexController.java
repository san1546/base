package com.example.eurakeclient.controller;

import com.example.eurakeclient.model.MailMenu;
import com.example.eurakeclient.service.MailMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private MailMenuService mailMenuService;

    @RequestMapping("/index")
    public String index(){
//        System.out.println("hh");
        List<MailMenu> mailMenus = mailMenuService.selectList();
        for(int i=0;i<mailMenus.size();i++)
        System.out.println(mailMenus.get(i).getMenuName());
        return "index";
    }

}