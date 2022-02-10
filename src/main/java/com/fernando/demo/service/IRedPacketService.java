package com.fernando.demo.service;

import com.fernando.demo.dto.RedPacketDto;

import java.math.BigDecimal;

public interface IRedPacketService {
    // 发红包核心业务逻辑
    String handOut(RedPacketDto dto) throws Exception;
    // 抢红包
    BigDecimal rob(Integer userId, String redId) throws Exception;
}
