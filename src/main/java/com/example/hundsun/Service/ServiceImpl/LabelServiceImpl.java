package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.LabelDao;
import com.example.hundsun.Service.LabelService;
import com.example.hundsun.domain.Labels;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LabelServiceImpl extends ServiceImpl<LabelDao, Labels> implements LabelService {
    @Autowired
    private LabelDao labelDao;
    /**
     * 查找项目的所有标签
     * @param p_id 项目id
     * @return 返回所有项目标签
     */
    @Override
    public List<Labels> getAllByP_id(int p_id) {
        QueryWrapper<Labels> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Labels::getP_id,p_id);
        return labelDao.selectList(wrapper);
    }

    /**
     * 创建新标签
     * @param labels 标签实体类
     * @return 返回lid
     */
    @Override
    public int create(Labels labels) {
        labelDao.insert(labels);
        return labels.getId();
    }
}
