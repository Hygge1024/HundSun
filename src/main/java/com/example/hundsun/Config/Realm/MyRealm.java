package com.example.hundsun.Config.Realm;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.hundsun.Service.LoginService;
import com.example.hundsun.domain.Permissions;
import com.example.hundsun.domain.Roles;
import com.example.hundsun.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private LoginService loginServicel;


    /**
     * 权限配置类
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        Users user = loginServicel.getUserByAccount(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for(Roles role:user.getRoles()) {
//            log.info("当前用户的角色有："+role.getRoleName());
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (Permissions permissions : role.getPermissions()) {
//                log.info("当前用户角色的权限有："+permissions.getPermissionName());
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionName());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 认证配置类
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(StringUtils.isEmpty((CharSequence) authenticationToken.getPrincipal())){
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        Users user = loginServicel.getUserByAccount(name);//获取数据库中的用户信息
        if (user == null) {
            //这里返回后会报出对应异常
            log.info("user = NULL");
            return null;
        }else{
            String salt = user.getSalt();//用户自身的随机盐
            //验证信息
            log.info("我正在进行验证，当前登录信息："+user.toString());
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    name,user.getPassword().toString(), ByteSource.Util.bytes(salt),getName());
            return simpleAuthenticationInfo;
        }
    }
}
