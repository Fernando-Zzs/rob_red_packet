package com.fernando.demo.dao;

import com.fernando.demo.entity.RedDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RedDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RedDetail record);

    RedDetail selectByPrimaryKey(Integer id);

    List<RedDetail> selectAll();

    int updateByPrimaryKey(RedDetail record);
}