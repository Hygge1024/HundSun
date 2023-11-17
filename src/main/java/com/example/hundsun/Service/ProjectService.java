package com.example.hundsun.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hundsun.domain.Projects;
import com.example.hundsun.domain.Users;
import com.example.hundsun.domain.Versions;

import java.util.List;

public interface ProjectService  extends IService<Projects> {
    /**
     * 获取所有项目
     * @return 返回所有项目
     */
    List<Projects> getAll();

    /**
     * 查询本项目所有版本
     */
    List<Versions> getVersionByP_id(int p_id);

    /**
     * 查询项目版本详情
     */
    Versions getOneVersionPro(int p_id,int v_id);

    /**
     * 发布项目-版本
     */
    int publishProVer(int p_id,int v_id);


    /**
     * 创建项目(需要选择 版本、类型)
     */
    int createProject(Projects project);

    /**
     * 更新项目（进度）
     */
    int updateState(int p_id,int projects);

    /**
     * 当前项目新建版本
     */
    int createVersion(int p_id);
    /**
     * 新添人员（）
     */
    int adduser(String Account,int p_id);
    /**
     * 查看项目中人员所有人员
     */
    List<Users> getAllUser(int p_id);
    /**
     * 查看项目中人员业务人员/标注人员/审核人员
     */
    List<Users> getTypeUser(int p_id,int role);

}
