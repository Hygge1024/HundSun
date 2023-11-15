package com.example.hundsun.Controller;

import com.example.hundsun.Service.Data_allService;
import com.example.hundsun.Service.LabelService;
import com.example.hundsun.Service.ResultsService;
import com.example.hundsun.Service.dataall_resultService;
import com.example.hundsun.Util.FileUtil;
import com.example.hundsun.Util.ImgClasUtil;
import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.domain.Data_all;
import com.example.hundsun.domain.Labels;
import com.example.hundsun.domain.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 业务人员的Controller
 */
@RestController
@RequestMapping("/api/Business")
@Slf4j
public class BusinessController {
    @Autowired
    private Data_allService dataAllService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private ImgClasUtil imgClasUtil;
    @Autowired
    private dataall_resultService dataall_ResultService;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ResultsService resultsService;

    private static String BaiDuAPI_KEY;
    private static String BaiDuSECRET_KEY;
    @Value("${baidu.APIkey}")
    public void setBaiDuAPI_KEY(String BaiDuAPI_KEY){
        BusinessController.BaiDuAPI_KEY = BaiDuAPI_KEY;
    }
    @Value("${baidu.Secretkey}")
    public void setBaiDuSECRET_KEY(String BaiDuSECRET_KEY){
        BusinessController.BaiDuSECRET_KEY = BaiDuSECRET_KEY;
    }


    /**
     * 图像识别-上传-并在data_all表中进行记录
     * @param multipartFile 文件
     * @param dataAll 项目和数据信息
     * @return  返回信息
     */
    @PostMapping("/upload/imgocr")
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
    @PostMapping("/upload/imgclas")
    public Result UploadData_Clas(@RequestParam("multipartFile")MultipartFile multipartFile, @ModelAttribute Data_all dataAll){
        int flag = dataAllService.UploadDate_all("imgclas",multipartFile,dataAll.getP_id(),dataAll.getDdescription());
        Integer code = flag != 0 ? Code.POST_OK : Code.POST_ERR;
        String msg = flag != 0 ? "上传成功" : "上传失败";
        return new Result(code,msg,null);
    }


    /**
     * 对图像分类-预标注
     * @param p_id 项目id
     * @param v_id 版本id
     * @return 返回预标注的数据量
     * @throws IOException 异常
     */
    @GetMapping("/pre/imgclas/{p_id}/{v_id}")
    public Result PreToMark(@PathVariable int p_id,@PathVariable int v_id) throws IOException {
        List<Data_all> dataAllList = dataAllService.getAllByP_id(p_id);//获取项目中所有已上传的资源
        log.info("用户上传的资源有: "+dataAllList);
        List<Labels> labelsList = labelService.getAllByP_id(p_id);//获取项目中所有标签
        log.info("本项目中的标签有: "+labelsList);
        int count  = labelsList.size();
        String[] label = new String[count];
        int i = 0;
        for(Labels labels : labelsList){
            label[i] = labels.getLabelName();
            i++;
        }
        i = 0;
        for (Data_all dataOne : dataAllList){
            if(dataall_ResultService.getByD_id(dataOne.getId()) == null){
                String[] ends = imgClasUtil.getImgClas(dataOne.getResourceUrl(),BaiDuAPI_KEY,BaiDuSECRET_KEY);
                String bestEnd = imgClasUtil.getCompare(label,ends);
                log.info("Did="+dataOne.getId()+"的识别结果为："+bestEnd);
                i++;
                if(bestEnd != null){
                    //将结果(lid)存入Results中，并在Dataall_result中进行记录
//                    String jsonString = "{\"identify\":\""+bestEnd+"\"}";
//                    String endName = bestEnd+".json";
//                    MultipartFile multipartFile = fileUtil.createMultipartFile(endName,jsonString);
//                    int r_id = dataall_ResultService.UploadResult("clasjson",multipartFile);
                    Results results = new Results();
                    results.setIsPre(1);
                    results.setL_id(dataall_ResultService.find(bestEnd,label));
                    int r_id = resultsService.create(results);
                    log.info("r_id= "+ r_id);
                    dataall_ResultService.insertOne(v_id,dataOne.getId(),r_id);
                }
            }

        }
        //应该将bestEnd结果生成json文件，存入Results表中去
        return new Result(Code.GET_OK,"预标注已完成,共预标注"+i+"个数据",null);
    }
}
