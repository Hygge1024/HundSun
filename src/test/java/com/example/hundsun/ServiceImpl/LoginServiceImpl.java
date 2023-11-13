package com.example.hundsun.ServiceImpl;

import com.example.hundsun.Service.LoginService;
import com.example.hundsun.domain.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceImpl {
    @Autowired
    private LoginService loginService;
    @Test
    void getUser(){
        System.out.println(loginService.getUserByAccount("1234"));
    }
    @Test
    void register() throws InterruptedException {
        Users user = new Users();
        user.setId(1);
        user.setAccount("1111");
        user.setPassword("123");
        loginService.register(user);
    }
    @Test
    void createAccount(){
        System.out.println(loginService.generateAccount());
        System.out.println(loginService.generateAccount());
    }
}
