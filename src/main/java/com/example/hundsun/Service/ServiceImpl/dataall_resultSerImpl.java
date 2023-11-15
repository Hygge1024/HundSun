package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.ResultsDao;
import com.example.hundsun.Dao.dataall_resultDao;
import com.example.hundsun.Service.dataall_resultService;
import com.example.hundsun.Util.MinIoUtil;
import com.example.hundsun.domain.Dataall_result;
import com.example.hundsun.domain.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class dataall_resultSerImpl extends ServiceImpl<dataall_resultDao, Dataall_result> implements dataall_resultService {
    @Autowired
    private dataall_resultDao dataallResultDao;
    @Autowired
    private ResultsDao resultsDao;
    @Autowired
    private MinIoUtil minIoUtil;
    @Override
    public Dataall_result getByD_id(int d_id) {
        QueryWrapper<Dataall_result> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Dataall_result::getD_id,d_id);
        return dataallResultDao.selectOne(wrapper);
    }
    /**
     * 上传文件到指定的Bucket
     * @param BucketName 文件桶
     * @param file 待上传文件
     * @return 返回资源存储的URL
     */
    @Override
    public String UploadFile(String BucketName, MultipartFile file) {
        String returnName = minIoUtil.upload(file,BucketName);
        String fileUrl = minIoUtil.getPresignedObjectUrlImg(returnName,BucketName);
        log.info("fileUrl: "+fileUrl);
        String SimpleUrl = fileUrl;
        if(fileUrl.contains("?")){
            SimpleUrl = fileUrl.substring(0,fileUrl.indexOf("?"));
        }
        log.info("SimpleUrl: "+ SimpleUrl);
        return SimpleUrl;
    }

    /**
     * 上传文件到指定位置，并将记录存储到results表中
     * @param BucketName 桶名
     * @param file 文件
     * @return 返回results表中的主键id
     */
    @Override
    public int UploadResult(String BucketName, MultipartFile file) {
        String SimpleUrl = this.UploadFile(BucketName,file);
        Results results = new Results();
        results.setResultUrl(SimpleUrl);
        results.setIsPre(1);
        resultsDao.insert(results);
        log.info("上传后端results："+results);
        return results.getId();
    }

    /**
     * 向dataall_result中插入数据
     * @param v_id 版本id
     * @param d_id 数据id
     * @param r_id 结果id
     * @return 返回操作数量
     */
    @Override
    public int insertOne(int v_id, int d_id, int r_id) {
        Dataall_result dataallResult = new Dataall_result();
        dataallResult.setV_id(v_id);
        dataallResult.setD_id(d_id);
        dataallResult.setR_id(r_id);
        return dataallResultDao.insert(dataallResult);
    }
    public int find(String findstr,String[] arr){
        for(int i = 0; i < arr.length; i++){
            if(findstr.equals(arr[i])){
                return i+1;
            }
        }
        return -1;
    }
}
