package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Types {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("typeName")
    private String typeName;
}
