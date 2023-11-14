package com.example.hundsun.Util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.hundsun.Config.MinIoConfig;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MinIoUtil {
    @Autowired
    private MinIoConfig prop;
    @Resource
    private MinioClient minioClient;
    @Autowired
    private SnowFlakeUtils snowFlakeUtils;

    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            return buckets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看文件对象
     * @return 存储bucket内文件对象信息
     */
    public List<Item> listObjects(String BucketName) {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(BucketName).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return items;
    }
    /**
     * 获取单个文件（文件名、Bucket名）
     */
    public String getPresignedObjectUrlImg(String objectname,String bucketName){
        String url = null;
        try {
            Map<String, String> reqParams = new HashMap<String, String>();
            reqParams.put("response-content-type", "image/png");//这里的具体参数需要根据实际情况而定

            url =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(bucketName)
                                    .object(objectname)
                                    .expiry(1, TimeUnit.HOURS)
                                    .extraQueryParams(reqParams)
                                    .build());
//            System.out.println(url);
        } catch (MinioException e) {
            System.err.println("Minio exception occurred: " + e);
        }  catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return url;
    }
    /**
     * 查看存储bucket是否存在
     * @return boolean
     */
    public Boolean bucketExists(String bucketName) {
        Boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }
    /**
     * 创建存储bucket
     * @return Boolean
     */
    public Boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 删除存储bucket
     * @return Boolean
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    public String upload(MultipartFile file, String BucketName) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)){
            throw new RuntimeException();
        }
        //改名称
        String fileName =  originalFilename.substring(originalFilename.lastIndexOf("."));
        String idName = snowFlakeUtils.getNextId()+"";
        String objectName =  idName + fileName;
        log.info("上传的文件名为："+objectName);
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(BucketName).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return objectName;
    }

    /**
     * 删除
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean remove(String fileName,String BucketName){
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(BucketName).object(fileName).build());
        }catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * 文件下载——未进行单元测试
     * @param fileName 文件名称
     * @param res response
     * @return Boolean
     */
    public void download(String fileName,String BucketName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(BucketName)
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len=response.read(buf))!=-1){
                    os.write(buf,0,len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                // 设置强制下载不打开
                // res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()){
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
