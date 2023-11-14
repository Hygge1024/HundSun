package com.example.hundsun.Util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class FileUtilTest {
    @Autowired
    private FileUtil fileUtil;
    @Test
    void StringToMultipartFile(){
        // 示例用法
        String fileName = "example.json";
        String jsonString = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

        MultipartFile multipartFile = fileUtil.createMultipartFile(fileName, jsonString);
        try {
            byte[] fileBytes = multipartFile.getBytes();
            String fileContent = new String(fileBytes);
            System.out.println("File Content:\n" + fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
