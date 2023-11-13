package com.example.hundsun.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Results {
    @TableId(type = IdType.AUTO)
    private int id;
    @TableField("resultUrl")
    private String resultUrl;
    @TableField("isPre")
    private int isPre;
    @TableField("isMark")
    private int isMark;
    @TableField("markTime")
    private Date markTime;
    @TableField("markAccount")
    private String markAccount;
    @TableField("isEx")
    private int isEx;
    @TableField("ExAccount")
    private String ExAccount;
    @TableField("ExTime")
    private Date ExTime;
    @TableField("l_id")
    private int l_id;
}
