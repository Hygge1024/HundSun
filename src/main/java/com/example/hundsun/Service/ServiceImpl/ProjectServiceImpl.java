package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.*;
import com.example.hundsun.Service.ProjectService;
import com.example.hundsun.domain.*;
import com.sun.jndi.ldap.Ber;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectsDao, Projects> implements ProjectService {

    @Autowired
    private ProjectsDao projectsDao;
    @Autowired
    private VersionsDao versionsDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private project_userDao project_userDao;
    @Autowired
    private user_roleDao user_roleDao;

    /**
     * 查看所有项目
     * @return 所有项目
     */
    @Override
    public List<Projects> getAll() {
        return projectsDao.selectList(null);
    }

    /**
     * 查询本项目所有版本
     */
    @Override
    public List<Versions> getVersionByP_id(int p_id) {
        QueryWrapper<Versions> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Versions::getP_id,p_id);
        return versionsDao.selectList(wrapper);
    }

    /**
     * 查询项目版本详情
     */
    @Override
    public Versions getOneVersionPro(int p_id, int v_id) {
        QueryWrapper<Versions> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Versions::getP_id,p_id)
                .eq(Versions::getId,v_id);
        return versionsDao.selectOne(wrapper);
    }
    /**
     * 发布项目-版本
     */
    @Override
    public int publishProVer(int p_id, int v_id) {
        Versions versions = this.getOneVersionPro(p_id,v_id);
        versions.setPublish(1);
        return versionsDao.updateById(versions);
    }


    /**
     * 创建项目(需要选择 版本、类型)
     * 初始创建，自动在versios表中创建对应的版本关系
     * @return 是否成功
     */
    @Override
    public int createProject(Projects project) {
        //时间属性
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Date currentDate = calendar.getTime();
        project.setPcreate(currentDate);
        projectsDao.insert(project);
        int p_id = project.getId();
        //向Versions表中初始化信息
        this.createVersion(p_id);
        return p_id;
    }

    /**
     * 当前项目 新建版本
     * @param p_id 项目id
     * @return 是否成功
     */
    @Override
    public int createVersion(int p_id) {
        Versions versions = new Versions();
        versions.setP_id(p_id);
        versionsDao.insert(versions);
        return versions.getId();
    }


    /**
     * 项目新添人员（）
     * @return 是否成功
     */
    @Override
    public int adduser(String Account,int p_id) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Users::getAccount, Account);
        Users users = usersDao.selectOne(wrapper);
        int u_id = users.getId();
        project_user projectUser = new project_user();
        projectUser.setP_id(p_id);
        projectUser.setU_id(u_id);
        return project_userDao.insert(projectUser);
    }

    /**
     * 查看项目中人员所有人员
     * @return 所有员工
     */
    @Override
    public List<Users> getAllUser(int p_id) {
        QueryWrapper<project_user> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(project_user::getP_id,p_id);
        List<project_user> projectUsers = project_userDao.selectList(wrapper);
        List<Users> usersList = new ArrayList<>();
        for (project_user projectUser:projectUsers){
            Users users = usersDao.selectById(projectUser.getU_id());
            usersList.add(users);
        }
        return usersList;
    }

    /**
     * 查看项目中人员业务人员/标注人员/审核人员
     * @return 所有业务人员
     */
    @Override
    public List<Users> getTypeUser(int p_id,int role) {
        QueryWrapper<project_user> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(project_user::getP_id,p_id);
        List<project_user> projectUsers = project_userDao.selectList(wrapper);
        List<Users> usersList = new ArrayList<>();
        for (project_user projectUser:projectUsers){
            int u_id = projectUser.getU_id();
            QueryWrapper<user_role> wrapper1  = new QueryWrapper<>();
            wrapper1.lambda()
                    .eq(user_role::getUserID,u_id)
                    .eq(user_role::getRoleID,role);
            if(null != user_roleDao.selectOne(wrapper1)){
                Users users = usersDao.selectById(u_id);
                usersList.add(users);
            }
        }
        return usersList;
    }

}
