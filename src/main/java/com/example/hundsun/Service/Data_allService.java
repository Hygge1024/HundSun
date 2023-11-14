package com.example.hundsun.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hundsun.domain.Data_all;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Data_allService extends IService<Data_all> {
    /**
     * 上传文件到指定的Bucket
     * @param BucketName 文件桶
     * @param file 待上传文件
     * @return 返回资源存储的URL
     */
    String UploadFile(String BucketName,MultipartFile file);
    /**
     * 上传原始数据
     * @param BucketName 桶名
     * @param file 文件
     * @param p_id 项目id
     * @param description 文件描述
     * @return 返回int（Controller层逻辑判断处理）
     */
    int UploadDate_all(String BucketName,MultipartFile file,int p_id,String description);

    /**
     * 查询所有原始数据-项目id
     * @param p_id
     * @return
     */
    List<Data_all> getAllByP_id(int p_id);

    /**
     * 查询单条原始数据-id
     * @param id
     * @return
     */
    Data_all getOneByID(int id);

}
