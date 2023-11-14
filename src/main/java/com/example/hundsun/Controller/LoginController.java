package com.example.hundsun.Controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.hundsun.Service.LoginService;
import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/login")
    public String login(){
        return "您还未请登录，进入登录界面";//可以进行页面的跳转
    }
    @PostMapping("/login")
    public Result login(@RequestBody Users user){
        if(StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())){
            return new Result(Code.GET_ERR,"请输入用户名或密码",null);
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getAccount(),
                user.getPassword()
        );
        try{
            //进行验证
            subject.login(usernamePasswordToken);
//            subject.checkRole("管理人员");
//            subject.checkPermissions("管理员权限","发布权限");

        }catch (UnknownAccountException e){
            log.error("用户名不存在",e);
            return new Result(Code.GET_ERR,"用户名不存在",null);
        } catch (AuthenticationException e){
            log.error("密码错误",e);
            return new Result(Code.GET_ERR,"密码错误",null);
        }catch (AuthorizationException e){
            log.error("没有权限",e);
            return new Result(Code.GET_ERR,"权限验证：没有权限",null);
        }
        Users userDB = loginService.getUserByAccount(user.getAccount());
        return new Result(Code.GET_OK,"登录成功",userDB);
    }
    @PostMapping("/register")
    public Result register(@RequestBody Users users) throws InterruptedException {
        return loginService.register(users);
    }

    @RequiresRoles("管理人员")
    @GetMapping("/admin")
    public Result admin(){
        return new Result(Code.GET_OK,"Admin权限成功",null);
    }
//    @RequiresRoles("query")
    @GetMapping("/index")
    public Result index(){
        return new Result(Code.GET_OK,"Query权限成功",null);
    }
    @RequiresRoles("发布权限")
    @GetMapping("/add")
    public Result add(){
        return new Result(Code.GET_OK,"Add权限成功",null);
    }
    @GetMapping("/error")
    public Result error(){
        return new Result(Code.GET_OK,"认证失败页面",null);
    }
}
