package com.example.hundsun.Util;

import com.example.hundsun.Exception.BusinessException;
import com.example.hundsun.Exception.SqlException;
import com.example.hundsun.Exception.SystemException;

import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

/**
 * 用于定义全局异常和全局数据绑定规则的类
 * 只写了功能，实际的具体异常没写
 */
@RestControllerAdvice
public class ProjectExceptionAdvice {



    /**
     * 异常处理
     * @param ex 对象
     * @return 返回Result
     */
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        ex.printStackTrace();//终端输出异常信息
        return new Result(ex.getCode(),null,ex.getMessage());
    }

    /**
     * 拦截 业务异常
     * @param ex 对象
     * @return 返回Result
     */
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex){
        ex.printStackTrace();//终端输出异常信息
        return new Result(ex.getCode(),null,ex.getMessage());
    }

    /**
     * 对注解验证角色和权限验证 进行异常捕获（由于注解验证角色和权限 无法捕获异常，因此添加一个拦截异常）
     * @param ex 对象
     * @return 返回Result
     */
    @ExceptionHandler
    public Result ErrorHandler(AuthorizationException ex){
        ex.printStackTrace();
        return new Result(Code.AUTHORITY_ERR,"没有通过权限验证！",ex.getMessage());
    }
    /**
     * 数据库方面的异常捕获（待验证）
     * @param ex 对象
     * @return 返回Result
     */
    @ExceptionHandler(SqlException.class)
    public Result sqlException(SqlException ex){
        ex.printStackTrace();
        return new Result(ex.getCode(),"数据库异常，操作失败!",ex.getMessage());
    }
    /**
     * 拦截 后端系统异常-一般是代码出现问题才出现
     * @param ex 对象
     * @return 返回Result
     */
    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        ex.printStackTrace();//终端输出异常信息
        return new Result(Code.SYSTEM_UNKNOW_ERR,"系统繁忙，请稍后再试！",ex.getMessage());
    }
}
