package com.example.hundsun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class HundSunApplication {
    public static void main(String[] args) {
        log.info("项目启动");
        SpringApplication.run(HundSunApplication.class, args);
        log.info("项目启动成功！！！");
    }

}
