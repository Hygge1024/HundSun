package com.example.hundsun.Util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SnowFlakeUtilsTest {
    @Autowired
    private SnowFlakeUtils snowFlakeUtils;
    @Test
    void getUnionId(){
        System.out.println(snowFlakeUtils.getNextId());
        System.out.println(snowFlakeUtils.getNextId());
        System.out.println(snowFlakeUtils.getNextId());
        System.out.println(snowFlakeUtils.getNextId());
        System.out.println(snowFlakeUtils.getNextId());
    }
}
