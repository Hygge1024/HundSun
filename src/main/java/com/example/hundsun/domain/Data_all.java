package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Data_all {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("p_id")
    private int p_id;
    @TableField("resourceUrl")
    private String resourceUrl;
    @TableField("ddescription")
    private String ddescription;
    @TableField("uploadDate")
    private Date uploadDate;
}
