package com.example.hundsun.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.hundsun.domain.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersDao extends BaseMapper<Users> {
}
