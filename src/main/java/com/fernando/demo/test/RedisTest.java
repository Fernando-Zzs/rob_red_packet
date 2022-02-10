package com.fernando.demo.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.demo.entity.PhoneUser;
import com.fernando.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {
    // 定义日志
    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

    // redis的操作组件
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // Json序列化与反序列化框架类
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void one(){
        log.info("===开始===");
        final String content = "RedisTemplate";
        final String key = "redis:template:one:string";

        ValueOperations valueOperations = redisTemplate.opsForValue();

        log.info("写入缓存的内容：{}", content);
        valueOperations.set(key, content);

        Object result = valueOperations.get(key);
        log.info("读取出来的内容：{}", result);
    }

    @Test
    public void two() throws Exception {
        log.info("===开始===");
        User user = new User(1, "阿修罗", 20);
        ValueOperations valueOperations = redisTemplate.opsForValue();

        final String key = "redis:template:two:object";
        final String content = objectMapper.writeValueAsString(user);

        log.info("写入缓存对象的信息：{}", user);
        valueOperations.set(key, content);

        Object result = valueOperations.get(key);
        if(result != null){
            User resultUser = objectMapper.readValue(result.toString(), User.class);
            log.info("读取缓存内容并且反向序列化后的结果：{}", resultUser);
        }
    }

    @Test
    public void three(){
        log.info("===开始===");
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();

        final String key = "redis:template:three:object";
        final String content = "StringRedisTemplate";

        log.info("写入对象的信息：{}", content);
        valueOperations.set(key, content);

        Object result = valueOperations.get(key);
        log.info("读取的内容：{}", result);
    }

    // 操作有序集合
    @Test
    public void four(){
        ArrayList<PhoneUser> list = new ArrayList<>();
        list.add(new PhoneUser("103", 130.0));
        list.add(new PhoneUser("101", 120.0));
        list.add(new PhoneUser("102", 80.0));
        list.add(new PhoneUser("105", 70.0));
        list.add(new PhoneUser("106", 50.0));
        list.add(new PhoneUser("104", 150.0));

        log.info("待处理数据：{}", list);

        final String key = "redis:test:4";
        redisTemplate.delete(key);

        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        for(PhoneUser u : list){
            zSetOperations.add(key, u, u.getFare());
        }
        Long size = zSetOperations.size(key);
        Set<PhoneUser> resSet = zSetOperations.reverseRange(key, 0L, size);
        for(PhoneUser u : resSet){
            log.info("读到的数据：{}", u);
        }
    }

    // TTL
    @Test
    public void five() throws InterruptedException {
        final String key1 = "redis:test:5";
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key1, "expire", 10L, TimeUnit.SECONDS);
        Thread.sleep(5000);
        Boolean existKey1 = redisTemplate.hasKey(key1);
        Object value = valueOperations.get(key1);
        log.info("等了5s后,是否还存在:{},值为:{}", existKey1, value);
        Thread.sleep(5000);
        Boolean existKey2 = redisTemplate.hasKey(key1);
        value = valueOperations.get(key1);
        log.info("再等了5s后,是否还存在:{},值为:{}", existKey1, value);

    }
}
