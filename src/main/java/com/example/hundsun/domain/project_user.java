package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class project_user {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("p_id")
    private int p_id;
    @TableField("u_id")
    private int u_id;
}
