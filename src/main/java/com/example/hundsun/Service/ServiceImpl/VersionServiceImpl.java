package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.VersionsDao;
import com.example.hundsun.Service.VersionService;
import com.example.hundsun.domain.Versions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VersionServiceImpl extends ServiceImpl<VersionsDao, Versions> implements VersionService {
}
