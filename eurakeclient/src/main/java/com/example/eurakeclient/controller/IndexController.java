package com.example.eurakeclient.controller;

import com.example.eurakeclient.model.Mail;
import com.example.eurakeclient.model.MailMenu;
import com.example.eurakeclient.service.IpConfiguration;
import com.example.eurakeclient.service.MailMenuService;
import com.example.eurakeclient.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;



//@RestController //显示返回的值
@Controller
public class IndexController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private MailMenuService mailMenuService;

    @Autowired
    private MailService mailService;

//    @Autowired
//    IpConfiguration ipConfiguration;
    @Value("${server.port}")
    String port;
    @RequestMapping("/hello")
    public String index(){
//        List<MailMenu> mailMenus = mailMenuService.selectList();
//        for(int i=0;i<mailMenus.size();i++)
//        System.out.println(mailMenus.get(i).getMenuName());
//        List<Mail> mails = mailService.selectByPrimaryKey(null);
//        for(int i=0;i<mails.size();i++)
//            System.out.println(mails.get(i).getContent());
//        return "index";
//        System.out.println(ipConfiguration.getPort());
//        return "index";
        return "hello,port:"+port;
    }

}