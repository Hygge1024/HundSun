package com.example.hundsun.ServiceImpl;

import com.example.hundsun.Service.ProjectService;
import com.example.hundsun.domain.Projects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectServiceTest {
    @Autowired
    private ProjectService projectService;
    @Test
    void getAll(){
        System.out.println(projectService.getAll());
    }
    @Test
    void getVersionByP_id(){
        System.out.println(projectService.getVersionByP_id(1));
    }
    @Test
    void getOneVersionPro(){
        System.out.println(projectService.getOneVersionPro(1,1));
    }
    @Test
    void publishProVer(){
        System.out.println(projectService.publishProVer(1,1));
    }
    @Test
    void createProject(){
        Projects projects = new Projects();
        projects.setProjectName("后端测试项目2");
        projects.setProjectType(3);
        projects.setPdescription("描述测试项目2");
        System.out.println(projectService.createProject(projects));
    }
    @Test
    void createVersion(){
        System.out.println(projectService.createVersion(1));
    }
    @Test
    void adduser(){
        System.out.println(projectService.adduser("2311154719",1));
    }
    @Test
    void getAllUser(){
        System.out.println(projectService.getAllUser(1));
    }
    @Test
    void getAllBusiness(){
        System.out.println(projectService.getTypeUser(1,1));
    }

}
