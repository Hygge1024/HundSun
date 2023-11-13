package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Roles {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("roleName")
    private String roleName;
    /**
     * 角色对应权限集合
     */
    @TableField(exist = false)
    private List<Permissions> permissions;
}
