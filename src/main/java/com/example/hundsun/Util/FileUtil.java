package com.example.hundsun.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
@Slf4j
public class FileUtil {
    /**
     * 字节输入流转换为包含二进制数据+文件名称的 MultipartFile 文件
     *
     * @param fileName     文件名
     * @param inputStream  字节输入流
     * @return MultipartFile
     * @throws IOException IO异常
     */
    public static MultipartFile inputStreamToMultipartFile(String fileName, InputStream inputStream) throws IOException {
        // 该方法只能用于测试
        // return new MockMultipartFile(fileName, fileName, MediaType.MULTIPART_FORM_DATA_VALUE, inputStream);

        // 创建 FileItem 对象
        FileItem fileItem = createFileItem(inputStream, fileName);

        // CommonsMultipartFile 是 Feign 对 MultipartFile 的封装，但是需要 FileItem 类对象
        return new CommonsMultipartFile(fileItem);
    }

    /**
     * 创建 FileItem 类对象
     *
     * @param inputStream 字节输入流
     * @param fileName    文件名
     * @return FileItem
     */
    public static FileItem createFileItem(InputStream inputStream, String fileName) {
        // 创建 FileItemFactory 对象
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "file";

        // 创建 FileItem 对象
        FileItem item = factory.createItem(textFieldName, MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[10 * 1024 * 1024];
        OutputStream os = null;

        // 使用输出流输出输入流的字节
        try {
            os = item.getOutputStream();
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("文件上传失败", e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return item;
    }
    /**
     * 将字符串转换为 MultipartFile 文件
     *
     * @param fileName   文件名
     * @param jsonString JSON 字符串
     * @return MultipartFile
     */
    public static MultipartFile createMultipartFile(String fileName, String jsonString) {
        try {
            // 将字符串转换为 InputStream
            InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes());

            // 使用 FileUtil 创建 MultipartFile
            return FileUtil.inputStreamToMultipartFile(fileName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("无法生成 MultipartFile 文件");
        }
    }
}
