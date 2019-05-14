package com.example.ribbon.controller;

import com.example.ribbon.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: hyh
 * @Date: Created by 17:28 2019/5/14
 * @Description:
 */
public class ribbonController {
    @Autowired
    IndexService service;

    @RequestMapping("/index")
    public String customer(){
        return service.helloService();
    }
}
