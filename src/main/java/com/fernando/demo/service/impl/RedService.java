package com.fernando.demo.service.impl;

import com.fernando.demo.dao.RedDetailMapper;
import com.fernando.demo.dao.RedRecordMapper;
import com.fernando.demo.dao.RedRobRecordMapper;
import com.fernando.demo.dto.RedPacketDto;
import com.fernando.demo.entity.RedDetail;
import com.fernando.demo.entity.RedRecord;
import com.fernando.demo.entity.RedRobRecord;
import com.fernando.demo.service.IRedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@EnableAsync
public class RedService implements IRedService {
    private static final Logger log = LoggerFactory.getLogger(RedService.class);

    @Autowired
    private RedRecordMapper redRecordMapper;

    @Autowired
    private RedDetailMapper redDetailMapper;

    @Autowired
    private RedRobRecordMapper redRobRecordMapper;

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordRedPacket(RedPacketDto dto, String redId, List<Integer> list) throws Exception {
        RedRecord redRecord = new RedRecord();
        redRecord.setUserId(dto.getUserId());
        redRecord.setRedPacket(redId);
        redRecord.setTotal(dto.getTotal());
        redRecord.setAmount(BigDecimal.valueOf(dto.getAmount()));
        redRecord.setCreateTime(new Date());

        redRecordMapper.insert(redRecord);

        RedDetail detail;
        // 由于红包记录都是一样的且是由数据库自增得到的id值 只能通过数据库操作得到对应id
        int id = redRecordMapper.findIdByUniqueString(redRecord.getRedPacket());
        for(Integer i : list){
            detail = new RedDetail();
            detail.setRecordId(id);
            detail.setAmount(BigDecimal.valueOf(i));
            detail.setCreateTime(new Date());

            redDetailMapper.insert(detail);
        }
    }

    @Override
    @Async
    public void recordRobRedPacket(Integer userId, String redId, BigDecimal amount) throws Exception {
        RedRobRecord redRobRecord = new RedRobRecord();
        redRobRecord.setUserId(userId);
        redRobRecord.setRedPacket(redId);
        redRobRecord.setAmount(amount);
        redRobRecord.setRobTime(new Date());

        redRobRecordMapper.insert(redRobRecord);
    }
}
