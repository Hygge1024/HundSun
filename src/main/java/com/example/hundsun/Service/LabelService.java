package com.example.hundsun.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hundsun.domain.Labels;

import java.util.List;

public interface LabelService extends IService<Labels> {
    /**
     * 查找项目的所有标签
     * @param p_id 项目id
     * @return 返回所有项目标签
     */
    List<Labels> getAllByP_id(int p_id);

    /**
     * 创建新标签
     * @param labels 标签实体类
     * @return 返回操作数
     */
    int create(Labels labels);

}
