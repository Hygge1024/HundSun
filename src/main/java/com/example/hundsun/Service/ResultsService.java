package com.example.hundsun.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hundsun.domain.Results;

public interface ResultsService extends IService<Results> {

    /**
     * 通过rid查找单条结果
     * @param rid
     * @return
     */
    Results getByID(int rid);

    /**
     * 更新 结果信息
     * @param results 新数据
     * @return
     */
    int update(Results results);

    /**
     * 上传结果记录
     * @param results 对象
     * @return 返回操作数量
     */
    int create(Results results);
}
