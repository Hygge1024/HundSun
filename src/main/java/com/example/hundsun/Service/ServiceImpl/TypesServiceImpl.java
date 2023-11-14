package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.typesDao;
import com.example.hundsun.Service.TypesService;
import com.example.hundsun.domain.Types;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TypesServiceImpl extends ServiceImpl<typesDao, Types> implements TypesService {
    @Autowired
    private typesDao typesDao;
    @Override
    public List<Types> getAllTypes() {
        return typesDao.selectList(null);
    }
}
