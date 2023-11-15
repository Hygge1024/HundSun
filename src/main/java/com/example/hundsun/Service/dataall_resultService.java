package com.example.hundsun.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.hundsun.domain.Dataall_result;
import org.springframework.web.multipart.MultipartFile;

public interface dataall_resultService  extends IService<Dataall_result> {
    Dataall_result getByD_id(int d_id);
    String UploadFile(String BucketName, MultipartFile file);
    int UploadResult(String BucketName, MultipartFile file);
    int insertOne(int v_id,int p_id,int r_id);
    int find(String findstr,String[] arr);
}
