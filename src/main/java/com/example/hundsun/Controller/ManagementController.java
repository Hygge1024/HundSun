package com.example.hundsun.Controller;

import com.example.hundsun.Service.ProjectService;
import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.domain.Projects;
import com.example.hundsun.domain.Users;
import com.example.hundsun.domain.Versions;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

/**
 * 项目管理人员的Controller
 */
@RestController
@RequestMapping("/api/Management")
@Slf4j
public class ManagementController {
    @Autowired
    private ProjectService projectService;

    /**
     * 查看所有项目
     */
    @GetMapping("/project")
    public Result getAll(){
        List<Projects> projectsList = projectService.getAll();
        Integer code = projectsList != null? Code.GET_OK:Code.GET_ERR;
        String msg = projectsList != null ? "查询所有项目成功":"查询失败";
        return new Result(code,msg,projectsList);
    }
    /**
     * 查询本项目所有版本
     */
    @GetMapping("/project/{p_id}")
    public Result getProByPro(@PathVariable int p_id){
        List<Versions> versionsList = projectService.getVersionByP_id(p_id);
        Integer code = versionsList != null? Code.GET_OK:Code.GET_ERR;
        String msg = versionsList != null ? "查询该项目所有版本成功":"查询失败";
        return new Result(code,msg,versionsList);
    }

    /**
     * 查询项目版本详情
     */
    @GetMapping("/project/{p_id}/{v_id}")
    public Result getOneVersionPro(@PathVariable int p_id,@PathVariable int v_id){
        Versions versions = projectService.getOneVersionPro(p_id,v_id);
        Integer code = versions != null? Code.GET_OK:Code.GET_ERR;
        String msg = versions != null ? "查询项目某个版本成功":"查询失败";
        return new Result(code,msg,versions);
    }
    /**
     * 发布项目-版本
     */
    @GetMapping("/project/publish/{p_id}/{v_id}")
    public Result publishProVer(@PathVariable int p_id,@PathVariable int v_id){
        int flag = projectService.publishProVer(p_id,v_id);
        Integer code = flag != 0? Code.GET_OK:Code.GET_ERR;
        String msg = flag != 0 ? "发布成功":"发布失败";
        return new Result(code,msg,null);
    }

    /**
     * 创建项目(需要选择 版本、类型)
     */
    @PostMapping("/project/create")
    public Result createProject(@RequestBody Projects projects){
        int p_id = projectService.createProject(projects);
        Integer code = p_id != 0? Code.GET_OK:Code.GET_ERR;
        String msg = p_id != 0 ? "创建项目成功，p_id如下":"创建项目失败";
        return new Result(code,msg,p_id);
    }

    /**
     * 当前项目新建版本
     */
    @GetMapping("/project/createVersion/{p_id}")
    public Result createVersion(@PathVariable int p_id){
        int v_id = projectService.createVersion(p_id);
        Integer code = v_id != 0? Code.GET_OK:Code.GET_ERR;
        String msg = v_id != 0 ? "项目版本创建成功，v_id如下":"创建项目失败";
        return new Result(code,msg,v_id);
    }

    /**
     * 项目新添人员（）
     */
    @GetMapping("/project/adduser/{Account}/{p_id}")
    public Result adduser(@PathVariable String Account,@PathVariable int p_id){
        int flag = projectService.adduser(Account,p_id);
        Integer code = flag != 0? Code.GET_OK:Code.GET_ERR;
        String msg = flag != 0 ? "添加成功":"添加失败";
        return new Result(code,msg,null);
    }

    /**
     * 查看项目中人员所有人员
     */
    @GetMapping("/project/getAllUsers/{p_id}")
    public Result getAllUser(@PathVariable int p_id){
        List<Users> usersList = projectService.getAllUser(p_id);
        Integer code = usersList != null? Code.GET_OK:Code.GET_ERR;
        String msg = usersList != null ? "查询所有员工成功":"查询失败";
        return new Result(code,msg,usersList);
    }

    /**
     * 查看项目中人员 1业务人员、2标注人员、3审核人员
     */
    @GetMapping("/project/getTypeUser/{p_id}/{role}")
    public Result getTypeUser(@PathVariable int p_id,@PathVariable int role){
        List<Users> usersList = projectService.getTypeUser(p_id,role);
        Integer code = usersList != null? Code.GET_OK:Code.GET_ERR;
        String msg = usersList != null ? "查询特定员工成功":"查询失败";
        return new Result(code,msg,usersList);
    }



}
