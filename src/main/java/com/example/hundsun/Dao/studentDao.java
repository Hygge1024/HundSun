package com.example.hundsun.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hundsun.domain.students;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface studentDao extends BaseMapper<students> {
}
