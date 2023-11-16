package com.example.hundsun.ServiceImpl;

import com.example.hundsun.Service.dataall_resultService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class dataall_resultSerImplTest {
    @Autowired
    private dataall_resultService dataallResultService;
    @Test
    void getByD_id(){
        System.out.println(dataallResultService.getByD_id(1));
    }
    @Test
    void getByPidVid(){
        System.out.println(dataallResultService.getByPidVid(5,1));
    }

}
