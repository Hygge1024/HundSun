package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.*;
import com.example.hundsun.Service.LoginService;
import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<UsersDao,Users> implements LoginService {
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private RolesDao rolesDao;
    @Autowired
    private user_roleDao user_roleDao;
    @Autowired
    private PermissionsDao permissionsDao;
    @Autowired
    private role_permissionsDao role_permissionsDao;

    /**
     * 获取用户信息，通过Account属性
     * @param userAccount 账号
     * @return 返回数据库中的用户
     */
    @Override
    public Users getUserByAccount(String userAccount) {
        //获取角色基本信息
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Users::getAccount,userAccount);
        Users users = usersDao.selectOne(wrapper);
        //如果无当前的用户，返回空
        if(users == null){
            return null;
        }

        //这里开始获取它的角色信息——用户对应的所有角色id
        List<user_role> user_roleList = user_roleList = user_roleDao.selectList(new QueryWrapper<user_role>()
                    .lambda()
                    .eq(user_role::getUserID,users.getId()));

        //角色id所对应的实际Role对象
        List<Roles> rolesList = new ArrayList<>();
        for(user_role u_r:user_roleList){
            rolesList.addAll(rolesDao.selectList(new QueryWrapper<Roles>()
                    .lambda()
                    .eq(Roles::getId,u_r.getRoleID())));
        }

        //这里获取角色的权限信息——角色对应的权限id
        List<role_permission> role_permissionsList = new ArrayList<>();
        for(Roles role : rolesList){
            //先给Roles对象Permission List初始化
            List<Permissions> permissionsList = new ArrayList<>();
            role.setPermissions(permissionsList);
            role_permissionsList.addAll(role_permissionsDao.selectList(new QueryWrapper<role_permission>()
                    .lambda()
                    .eq(role_permission::getRoleID,role.getId())));
        }
        //获取具体的权限类
        for(role_permission r_p:role_permissionsList){
            Permissions permissions = permissionsDao.selectOne(new QueryWrapper<Permissions>()
                    .lambda()
                    .eq(Permissions::getId, r_p.getPermissionID()));//获取单个角色的权限
//            将权限赋值给角色对象Role（根据roleID找到Role对象，然后将权限add进去）
            int roleID = r_p.getRoleID();//需要将上面的Permissions赋值给Role对象的List属性
            Optional<Roles> first = rolesList.stream().filter(Roles -> Objects.equals(Roles.getId(),roleID)).findFirst();
            if(first.isPresent()){
                first.get().getPermissions().add(permissions);//追加权限
            }
        }
//        System.out.println("用户信息:"+users);
//        System.out.println("用户——角色表:"+user_roleList);
//        System.out.println("角色信息:"+rolesList);
//        System.out.println("角色——权限信息:"+role_permissionsList);
//        System.out.println("权限信息："+permissionsList);
        users.setRoles(rolesList);
        return users;
    }

    /**
     * 注册功能
     * @param users 用户输入的users
     * @return 返回Result类型的结果
     * @throws InterruptedException 时间类型异常
     */
    @Override
    public Result register(Users users) throws InterruptedException {
        while(true){
            //自动生成用户账号
            String userAccount = generateAccount();
            users.setAccount(userAccount);
            QueryWrapper<Users> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Users::getAccount,userAccount);
            if(usersDao.selectOne(wrapper) == null){
                log.info("生成账号为:"+userAccount);
                break;//不重复，退出循环
            }else{
                sleep(1);
            }
        }
        //获取用户输入的密码
        String password = users.getPassword();
        //给用户生成随机的盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        log.info("salt: "+ salt);
        int times = 2;
        String algorithmName = "md5";
        String endPassword = new SimpleHash(algorithmName,password,salt,times).toString();
        log.info("end密码："+endPassword);
        users.setPassword(endPassword);
        users.setSalt(salt);
        //对用户存储
        usersDao.insert(users);
        //对user_role表进行记录
        Users returnUser = this.getUserByAccount(users.getAccount());
        log.info("userID"+returnUser.getId());
        log.info("RoleID"+users.getRoles().get(0).getId());
        user_role u_r = new user_role();
        u_r.setUserID(returnUser.getId());
        u_r.setRoleID(users.getRoles().get(0).getId());
        user_roleDao.insert(u_r);
        return new Result(Code.POST_OK,"创建成功！",this.getUserByAccount(users.getAccount()));

    }

    /**
     * 生产随机账号
     * @return 返回新的账号
     */
    public String generateAccount(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String timestamp = dateFormat.format(new Date()).substring(2,8);
        long currenttime = System.currentTimeMillis();
        int lastFoutDigtis = (int) (currenttime % 10000);
        String account = timestamp+lastFoutDigtis;
        return account;
    }


}
