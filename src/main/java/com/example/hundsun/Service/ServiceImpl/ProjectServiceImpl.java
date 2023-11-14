package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.ProjectsDao;
import com.example.hundsun.Service.ProjectService;
import com.example.hundsun.domain.Projects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectsDao, Projects> implements ProjectService {

}
