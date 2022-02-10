package com.fernando.demo.dao;

import com.fernando.demo.entity.RedRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RedRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RedRecord record);

    RedRecord selectByPrimaryKey(Integer id);

    List<RedRecord> selectAll();

    int updateByPrimaryKey(RedRecord record);

    // 根据全局唯一红包标识串查找id
    int findIdByUniqueString(String unique);
}