package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Versions {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("p_id")
    private int p_id;
    @TableField("version")
    private String version;
    @TableField("publish")
    private int publish;
}
