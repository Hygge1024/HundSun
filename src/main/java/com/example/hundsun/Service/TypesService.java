package com.example.hundsun.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hundsun.domain.Types;

import java.util.List;

public interface TypesService extends IService<Types> {
    /**
     * 查询所有的类型
     * @return 返回所有的类别
     */
    List<Types> getAllTypes();

}
