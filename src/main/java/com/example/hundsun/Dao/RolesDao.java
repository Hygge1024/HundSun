package com.example.hundsun.Dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hundsun.domain.user_role;
import org.apache.ibatis.annotations.Mapper;
import com.example.hundsun.domain.Roles;

import java.util.List;

@Mapper
public interface RolesDao extends BaseMapper<Roles> {
//    List<user_role> selectList(LambdaQueryWrapper<user_role> eq);
}
