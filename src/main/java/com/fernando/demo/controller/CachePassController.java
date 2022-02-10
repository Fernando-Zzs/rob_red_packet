package com.fernando.demo.controller;

import com.fernando.demo.service.CachePassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CachePassController {
    private static final Logger log = LoggerFactory.getLogger(CachePassController.class);
    private static final String prefix = "cache/pass";

    @Autowired
    private CachePassService cachePassService;

    @RequestMapping(value = prefix + "/item/info", method = RequestMethod.GET)
    public Map<String, Object> getItem(@RequestParam String itemCode){
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "成功");
        try {
            resMap.put("data", cachePassService.getItemInfo(itemCode));
        } catch (Exception e) {
            resMap.put("code", -1);
            resMap.put("msg", "失败: " + e.getMessage());
        }
        return resMap;
    }
}
