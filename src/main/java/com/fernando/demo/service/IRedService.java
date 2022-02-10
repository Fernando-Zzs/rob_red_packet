package com.fernando.demo.service;

import com.fernando.demo.dto.RedPacketDto;

import java.math.BigDecimal;
import java.util.List;

public interface IRedService {
    // 记录发红包的相关信息入数据库
    void recordRedPacket(RedPacketDto dto, String redId, List<Integer>list) throws Exception;

    // 记录抢红包的相关信息入数据库
    void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception;
}
