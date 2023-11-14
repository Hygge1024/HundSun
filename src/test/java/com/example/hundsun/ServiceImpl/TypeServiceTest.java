package com.example.hundsun.ServiceImpl;

import com.example.hundsun.Service.TypesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TypeServiceTest {
    @Autowired
    private TypesService typesService;
    @Test
    void getAllList(){
        System.out.println(typesService.getAllTypes());
    }
}
