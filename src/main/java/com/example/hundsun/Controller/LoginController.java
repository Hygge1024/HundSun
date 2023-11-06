package com.example.hundsun.Controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.domain.user;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {
    @GetMapping("/login")
    public Result login(user user){
        if(StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())){
            return new Result(Code.GET_ERR,"请输入用户名或密码",null);
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try{
            //进行验证
            subject.login(usernamePasswordToken);
            subject.checkRole("admin");
            subject.checkPermissions("query","add");
        }catch (UnknownAccountException e){
            log.error("用户名不存在",e);
            return new Result(Code.GET_ERR,"用户名不存在",null);
        } catch (AuthenticationException e){
            log.error("账号或密码错误",e);
            return new Result(Code.GET_ERR,"账号或密码错误",null);
        }catch (AuthorizationException e){
            log.error("没有权限",e);
            return new Result(Code.GET_ERR,"权限验证：没有权限",null);
        }
        return new Result(Code.GET_OK,"登录成功",user);
    }

    @RequiresRoles("admin")
    @GetMapping("/admin")
    public Result admin(){
        return new Result(Code.GET_OK,"Admin权限成功",null);
    }
//    @RequiresRoles("query")
    @GetMapping("/index")
    public Result index(){
        return new Result(Code.GET_OK,"Query权限成功",null);
    }
    @RequiresRoles("add")
    @GetMapping("/add")
    public Result add(){
        return new Result(Code.GET_OK,"Add权限成功",null);
    }
}
