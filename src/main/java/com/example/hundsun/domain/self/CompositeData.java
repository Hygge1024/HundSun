package com.example.hundsun.domain.self;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.hundsun.domain.Data_all;
import com.example.hundsun.domain.Results;
import lombok.Data;

import java.util.Date;

/**
 * 在预标注后 返回给数据标注人员的类（主要结合了dataall 和 result）
 */
@Data
public class CompositeData {
    private Results results;
    private Data_all dataAll;

}
