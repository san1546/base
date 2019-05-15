package com.example.ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: hyh
 * @Date: Created by 17:30 2019/5/14
 * @Description:
 */

@Service
public class IndexService {
    @Autowired
    RestTemplate restTemplate;

    public String helloService() {
        return restTemplate.getForObject("http://EURAKE-CLIENT/hello", String.class);
    }

}
