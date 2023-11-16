package com.example.hundsun.ServiceImpl;

import com.example.hundsun.Service.Data_allService;
import com.example.hundsun.domain.Data_all;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
public class Data_allServiceTest {
    @Autowired
    private Data_allService dataAllService;
    @Test
    void uploadFile(){
        String filePath ="C:\\Users\\刘\\Desktop\\Diagram2.png";
        File file = new File(filePath);
        MultipartFile multipartFile = null;
        try (FileInputStream input = new FileInputStream(file)){
            // 创建MockMultipartFile对象
            multipartFile = new MockMultipartFile(
                    "file", // 表单字段名称
                    file.getName(), // 文件名
                    "image/png", // 文件类型
                    input // 文件输入流
            );
            System.out.println(dataAllService.UploadFile("imgocr",multipartFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void uploadToData_all(){
        String filePath ="C:\\Users\\刘\\Desktop\\Diagram2.png";
        File file = new File(filePath);
        MultipartFile multipartFile = null;
        try (FileInputStream input = new FileInputStream(file)){
            // 创建MockMultipartFile对象
            multipartFile = new MockMultipartFile(
                    "file", // 表单字段名称
                    file.getName(), // 文件名
                    "image/png", // 文件类型
                    input // 文件输入流
            );
            int flag = dataAllService.UploadDate_all("imgocr",multipartFile,1,"后端自己测试数据lt");
            System.out.println("返回数据int: " + flag);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void getAllByP_id(){
        System.out.println(dataAllService.getAllByP_id(1));
    }
    @Test
    void getOneByID(){
        System.out.println(dataAllService.getOneByID(1));
    }
    @Test
    void getPageByP_id(){
        System.out.println(dataAllService.getPageByP_id(1,1,5).getRecords());
    }
}
