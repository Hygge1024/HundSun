package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.ResultsDao;
import com.example.hundsun.Service.ResultsService;
import com.example.hundsun.domain.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResultServiceImpl extends ServiceImpl<ResultsDao,Results> implements ResultsService {
    @Autowired
    private ResultsDao resultsDao;
    @Override
    public Results getByID(int rid) {
        return resultsDao.selectById(rid);
    }

    @Override
    public int update(Results results) {
        QueryWrapper<Results> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Results::getId,results.getId());
        return resultsDao.update(results,wrapper);
    }
    /**
     * 上传结果记录
     * @param results 对象
     * @return 返回操作数量
     */
    @Override
    public int create(Results results) {
        resultsDao.insert(results);
        return results.getId();
    }
}
