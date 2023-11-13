package com.example.hundsun.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.domain.Users;

public interface LoginService extends IService<Users> {
  Users getUserByAccount(String userAccount);
  Result register(Users users) throws InterruptedException;
  String generateAccount();
}
