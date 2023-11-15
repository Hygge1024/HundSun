package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Labels {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("labelName")
    private String labelName;
    @TableField("ltype")
    private String ltype;
    @TableField("p_id")
    private int p_id;
    @TableField("v_id")
    private int v_id;
    @TableField("description")
    private String description;
}
