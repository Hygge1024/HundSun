package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Dataall_result {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("v_id")
    private int v_id;
    @TableField("d_id")
    private int d_id;
    @TableField("r_id")
    private int r_id;
}
