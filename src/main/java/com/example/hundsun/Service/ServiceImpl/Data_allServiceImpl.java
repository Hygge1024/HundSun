package com.example.hundsun.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hundsun.Dao.Data_allDao;
import com.example.hundsun.Service.Data_allService;
import com.example.hundsun.Util.MinIoUtil;
import com.example.hundsun.domain.Data_all;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@Service
@Slf4j
/*
 * 主要写对对象的操作，其他操作在Controller层中写
 */
public class Data_allServiceImpl extends ServiceImpl<Data_allDao, Data_all> implements Data_allService {
    @Autowired
    private MinIoUtil minIoUtil;
    @Autowired
    private Data_allDao dataAllDao;

    /**
     * 上传文件到指定的Bucket
     * @param BucketName 文件桶
     * @param file 待上传文件
     * @return 返回资源存储的URL
     */
    @Override
    public String UploadFile(String BucketName,MultipartFile file) {
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
     * 上传原始数据
     * @param BucketName 桶名
     * @param file 文件
     * @param p_id 项目id
     * @param description 文件描述
     * @return 返回int（Controller层逻辑判断处理）
     */
    @Override
    public int UploadDate_all(String BucketName, MultipartFile file, int p_id, String description) {
        String SimpleUrl = this.UploadFile(BucketName,file);

        //时间属性
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Date currentDate = calendar.getTime();
        log.info("当前时间为："+currentDate);

        Data_all dataAll = new Data_all();
        dataAll.setP_id(p_id);
        dataAll.setResourceUrl(SimpleUrl);
        dataAll.setDdescription(description);
        dataAll.setUploadDate(currentDate);
        log.info("上传的数据dataAll："+dataAll);
        return dataAllDao.insert(dataAll);
    }
    /**
     * 查询所有原始数据-项目id
     * @param p_id 项目id
     * @return 该项目的所有数据
     */
    @Override
    public List<Data_all> getAllByP_id(int p_id) {
        QueryWrapper<Data_all> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Data_all::getP_id,p_id);
        return dataAllDao.selectList(wrapper);
    }
    /**
     * 查询单条原始数据-id
     * @param id 数据id
     * @return 返回单条原始数据
     */
    @Override
    public Data_all getOneByID(int id) {
        return dataAllDao.selectById(id);
    }
    /**
     * 分页查询-通过项目id
     * @param p_id 项目id
     * @param currentPage 当前页
     * @param pageSize 每页数量
     * @return 返回查询结果
     */
    @Override
    public IPage<Data_all> getPageByP_id(int p_id, int currentPage, int pageSize) {
        IPage page = new Page(currentPage,pageSize);
        QueryWrapper<Data_all> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Data_all::getP_id,p_id);
        return dataAllDao.selectPage(page,wrapper);
    }


}
