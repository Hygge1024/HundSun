package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Projects {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("projectName")
    private String projectName;
    @TableField("projectType")
    private int projectType;
    @TableField("pdescription")
    private String pdescription;
    @TableField("pcreate")
    private Data pcreate;
    @TableField("pdeadline")
    private Data pdeadline;
}
