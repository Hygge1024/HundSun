package com.example.hundsun.Service.ServiceImpl;

import com.example.hundsun.Service.LoginService;
import com.example.hundsun.domain.Permissions;
import com.example.hundsun.domain.Role;
import com.example.hundsun.domain.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Override
    public user getUserByName(String getMapByName) {
        return getMapByName(getMapByName);
    }

    /**
     * 模拟数据库查询
     * @param userName
     * @return
     */
    public  user getMapByName(String userName){
        //角色一
        //模拟获取所有权限
        Permissions permissions1 = new Permissions("1","query");
        Permissions permissions2 = new Permissions("2","add");
        Set<Permissions> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        //模拟角色——该角色具有的权限
        Role role = new Role("1","admin",permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        //模拟用户
        user user = new user("1","user001","123456",roleSet);
        Map<String,user> map = new HashMap<>();
        map.put(user.getUserName(),user);

        //角色二
        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions1);
        Role role1 = new Role("2", "user", permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        user user1 = new user("2", "zhangsan", "123456", roleSet1);
        map.put(user1.getUserName(), user1);
        return map.get(userName);


    }

}
