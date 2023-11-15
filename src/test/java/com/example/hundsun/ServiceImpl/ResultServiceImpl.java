package com.example.hundsun.ServiceImpl;

import com.example.hundsun.Service.ResultsService;
import com.example.hundsun.domain.Results;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResultServiceImpl {
    @Autowired
    private ResultsService resultsService;
    @Test
    void getByID(){
        System.out.println(resultsService.getByID(1));
    }
    @Test
    void update(){
        Results results = new Results();
        results.setId(1);
        results.setResultUrl("更新后的URL");
        results.setIsPre(2);
        System.out.println(resultsService.update(results));
    }
    @Test
    void create(){
        Results results = new Results();
        results.setResultUrl("更新后的URL");
        results.setMarkAccount("321");
        results.setIsPre(1);
        results.setExAccount("123");
        System.out.println(resultsService.create(results));
    }

}
