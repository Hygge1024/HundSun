package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class user_role {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("userID")
    private int userID;
    @TableField("roleID")
    private int roleID;
}
