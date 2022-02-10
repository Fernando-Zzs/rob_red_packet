package com.fernando.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.demo.controller.CachePassController;
import com.fernando.demo.dao.ItemMapper;
import com.fernando.demo.entity.Item;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CachePassService {
    private static final Logger log = LoggerFactory.getLogger(CachePassService.class);

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String keyPrefix = "item:";

    public Item getItemInfo(String itemCode) throws Exception {
        Item item = null;

        final String key = keyPrefix + itemCode;

        ValueOperations valueOperations = redisTemplate.opsForValue();
        if(redisTemplate.hasKey(key)){
            log.info("====获取商品详情-缓存中存在该商品-商品编号为：{}", itemCode);

            Object res = valueOperations.get(key);
            if(res != null && !Strings.isBlank(res.toString())){
                item = objectMapper.readValue(res.toString(), Item.class);
            }
        } else {
            log.info("====获取商品详情-缓存中不存在该商品-商品编号为：{}", itemCode);
            item = itemMapper.selectByCode(itemCode);
            if(item != null){
                valueOperations.set(key, objectMapper.writeValueAsString(item));
            } else {
                // 避免缓存击穿
                valueOperations.set(key, "", 30L, TimeUnit.MINUTES);
            }
        }
        return item;
    }
}
