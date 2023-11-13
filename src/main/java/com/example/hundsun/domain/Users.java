package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Users {
    @TableId(type = IdType.AUTO)
    private int id;//唯一id
    @TableField("name")
    private String name;//姓名
    @TableField("counts")
    private int counts;//项目数
    @TableField("phone")
    private String phone;//电话
    @TableField("password")
    private String password;//密码
    @TableField("account")
    private String account;//账号
    @TableField("salt")
    private String salt;//随机盐
    /**
     * 用户对应的角色集合
     */
    @TableField(exist = false)
    private List<Roles> roles;
}
