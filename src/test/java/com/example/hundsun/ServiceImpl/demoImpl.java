package com.example.hundsun.ServiceImpl;

import com.example.hundsun.Dao.studentDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class demoImpl {
    @Autowired
    private studentDao studentDao;
    //日志记录器
    Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    void getall(){
        System.out.println(studentDao.selectList(null));
    }
     @Test
    void getLog(){
        //日志的级别（小到大） 默认是info及以后的级别
       logger.trace("这是trace日志");
       logger.debug("这是debug日志");
       logger.info("这是info日志");
       logger.warn("这是warn信息");
       logger.error("这是error日志");
     }
}
