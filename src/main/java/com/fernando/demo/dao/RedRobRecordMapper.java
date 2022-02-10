package com.fernando.demo.dao;

import com.fernando.demo.entity.RedRobRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RedRobRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RedRobRecord record);

    RedRobRecord selectByPrimaryKey(Integer id);

    List<RedRobRecord> selectAll();

    int updateByPrimaryKey(RedRobRecord record);
}