package com.example.hundsun.Util;

import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class MinIoUtilTest {
    @Autowired
    private MinIoUtil minioUtil;
    @Test
    void contextLoads() {
    }

    //	获取全部bucket
    @Test
    void getAllBuckets(){
        List<Bucket> list = minioUtil.getAllBuckets();
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Bucket名："+list.get(i).name());
        }
    }

    //	存储bucket内文件对象信息
    @Test
    void listObjects(){
        List<Item> list = minioUtil.listObjects("imgall");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("文件名："+list.get(i).objectName()+"，文件大小："+list.get(i).size()+"文件etag:"+list.get(i).etag());
        }
    }

    //	查看存储bucket是否存在
    @Test
    void bucketExists(){
        System.out.println(minioUtil.bucketExists("imgall"));
    }

    //	创建存储bucket
    @Test
    void makeBucket(){
        System.out.println(minioUtil.makeBucket("textallaa"));
    }

    //	删除存储bucket
    @Test
    void removeBucket(){
        System.out.println(minioUtil.removeBucket("textallaa"));
    }

    //	文件上传
    @Test
    void upload(){
        String filePath ="C:\\Users\\刘\\Desktop\\tdn.jpg";
        File file = new File(filePath);
        MultipartFile multipartFile = null;
        try (FileInputStream input = new FileInputStream(file)){
            // 创建MockMultipartFile对象
            multipartFile = new MockMultipartFile(
                    "file", // 表单字段名称
                    file.getName(), // 文件名
                    "text/plain", // 文件类型
                    input // 文件输入流
            );
            minioUtil.upload(multipartFile,"imgclas");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //	删除具体文件
    @Test
    void remove(){
        System.out.println(minioUtil.remove("自己测试长传的文件+jzg.jpg","imgclas"));
    }

    //获取单个对象的url地址
    @Test
    void getPresignedObjectUrl(){
        System.out.println(minioUtil.getPresignedObjectUrlImg("img_1.png","imgocr"));
    }

}
