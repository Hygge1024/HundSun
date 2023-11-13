package com.example.hundsun.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hundsun.domain.Labels;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LabelDao extends BaseMapper<Labels> {
}
