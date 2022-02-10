package com.fernando.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTest {
    @RequestMapping("/redisTest")
    public String redisTest(){
        return "nihao";
    }
}
