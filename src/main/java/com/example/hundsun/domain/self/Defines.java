package com.example.hundsun.domain.self;

import lombok.Data;

/**
 * 用于业务人员定义 样例（数据id+创建标签）
 */
@Data
public class Defines {
    private int p_id;//当前项目的id
    private int v_id;//当前项目的版本id
    private int d_id;//当前数据的id
    private String resourceUrl;//当前数据的URL
    private String labelName;//新建的Label标签名
    private String description;//标签描述
    private String ltype;//标签类型（文本可能会用到）
}
