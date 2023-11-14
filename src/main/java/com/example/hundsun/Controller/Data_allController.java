package com.example.hundsun.Controller;

import com.example.hundsun.Service.Data_allService;
import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.domain.Data_all;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/data")
@Slf4j
public class Data_allController {
    @Autowired
    private Data_allService dataAllService;

    /**
     * 图像识别-上传-并在data_all表中进行记录
     * @param multipartFile 文件
     * @param dataAll 项目和数据信息
     * @return  返回信息
     */
    @PostMapping("/imgocr")
    public Result UploadData_Ocr(@RequestParam("multipartFile")MultipartFile multipartFile, @ModelAttribute Data_all dataAll){
        int flag = dataAllService.UploadDate_all("imgocr",multipartFile,dataAll.getP_id(),dataAll.getDdescription());
        Integer code = flag != 0 ? Code.POST_OK : Code.POST_ERR;
        String msg = flag != 0 ? "上传成功" : "上传失败";
        return new Result(code,msg,null);
    }
    /**
     * 图像分类-上传-在data_all表中进行记录
     * @param multipartFile 文件
     * @param dataAll 项目和数据信息
     * @return  返回信息
     */
    @PostMapping("/imgclas")
    public Result UploadData_Clas(@RequestParam("multipartFile")MultipartFile multipartFile, @ModelAttribute Data_all dataAll){
        int flag = dataAllService.UploadDate_all("imgclas",multipartFile,dataAll.getP_id(),dataAll.getDdescription());
        Integer code = flag != 0 ? Code.POST_OK : Code.POST_ERR;
        String msg = flag != 0 ? "上传成功" : "上传失败";
        return new Result(code,msg,null);
    }
}
