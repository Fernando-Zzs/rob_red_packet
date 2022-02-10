package com.fernando.demo.service.impl;

import com.fernando.demo.dto.RedPacketDto;
import com.fernando.demo.service.IRedPacketService;
import com.fernando.demo.service.IRedService;
import com.fernando.demo.utils.RedPacketUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedPacketService implements IRedPacketService {
    private static final Logger log = LoggerFactory.getLogger(RedPacketService.class);
    private static final String keyPrefix = "redis:red:packet:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IRedService redService;

    // 发红包
    @Override
    public String handOut(RedPacketDto dto) throws Exception {
        //参数合法性判断
        if(dto.getTotal() > 0 && dto.getAmount() > 0){
            List<Integer> list = RedPacketUtil.divideRedPackage(dto.getAmount(), dto.getTotal());
            // 生成红包全局唯一标识串
            String timestamp = String.valueOf(System.nanoTime());
            // 生成Redis的key 用于存储该金额列表
            String redId = new StringBuffer(keyPrefix).append(dto.getUserId()).append(":").append(timestamp).toString();
            redisTemplate.opsForList().leftPushAll(redId, list);

            // 生成Redis的key 用于存储红包的总数
            String redTotalKey = redId + ":total";
            redisTemplate.opsForValue().set(redTotalKey, dto.getTotal());

            // 异步存入数据库
            redService.recordRedPacket(dto, redId, list);
            return redId;
        } else {
            throw new Exception("系统异常-分发红包-参数不合法");
        }
    }

    // 抢红包处理逻辑
    @Override
    public BigDecimal rob(Integer userId, String redId) throws Exception {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 处理用户抢红包时要先判断该用户是否已经抢过了
        Object obj = valueOperations.get(redId + userId + ":rob");
        if(obj != null) return new BigDecimal(obj.toString());

        // 判断缓存系统中是否仍有红包
        Boolean res = click(redId);
        if(res){
            // 这里需要上分布式锁，因为一个红包每个人只能抢一次
            final String lockKey = redId + userId + "-lock";
            Boolean lock = valueOperations.setIfAbsent(lockKey, redId);
            redisTemplate.expire(lockKey, 24L, TimeUnit.HOURS);

            try {
                // 如果当前线程获得到该分布式锁
                if(lock){
                    // 拆红包业务逻辑
                    Object value = redisTemplate.opsForList().rightPop(redId);
                    // 当前弹出的随机金额不为空
                    if(value != null){
                        // 红包数量更新
                        String redTotalKey = redId + ":total";
                        Integer currTotal = valueOperations.get(redTotalKey) != null ? (Integer) valueOperations.get(redTotalKey) : 0;
                        valueOperations.set(redTotalKey, currTotal - 1);

                        // 单位处理
                        BigDecimal result = new BigDecimal(value.toString()).divide(new BigDecimal(100));
                        // 相关记录存入数据库
                        redService.recordRobRedPacket(userId, redId, new BigDecimal(value.toString()));
                        // 相关记录存入缓存系统
                        valueOperations.set(redId + userId + ":rob", result, 24L, TimeUnit.HOURS);

                        log.info("当前用户抢到红包了：userId={}, key={}, 金额={}", userId, redId, result);
                        return result;
                    }
                }
            } catch (Exception e) {
                throw new Exception("系统异常-抢红包-加分布式锁失败！");
            }
        }
        return null;
    }

    // 根据传入的redId 去缓存查询是否还有剩余红包 返回true 表示还有
    public Boolean click(String redId) throws Exception{
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String redTotalKey = redId + ":total";
        Object total = valueOperations.get(redTotalKey);
        if(total != null && Integer.valueOf(total.toString()) > 0){
            return true;
        }
        return false;
    }
}
