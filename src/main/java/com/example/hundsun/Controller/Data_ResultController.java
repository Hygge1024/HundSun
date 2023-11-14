package com.example.hundsun.Controller;

import com.example.hundsun.Service.Data_allService;
import com.example.hundsun.Service.LabelService;
import com.example.hundsun.Service.dataall_resultService;
import com.example.hundsun.Util.FileUtil;
import com.example.hundsun.Util.ImgClasUtil;
import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.Util.SnowFlakeUtils;
import com.example.hundsun.domain.Data_all;
import com.example.hundsun.domain.Labels;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/pre")
@Slf4j
public class Data_ResultController {
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
    private SnowFlakeUtils snowFlakeUtils;

    private static String BaiDuAPI_KEY;
    private static String BaiDuSECRET_KEY;
    @Value("${baidu.APIkey}")
    public void setBaiDuAPI_KEY(String BaiDuAPI_KEY){
        Data_ResultController.BaiDuAPI_KEY = BaiDuAPI_KEY;
    }
    @Value("${baidu.Secretkey}")
    public void setBaiDuSECRET_KEY(String BaiDuSECRET_KEY){
        Data_ResultController.BaiDuSECRET_KEY = BaiDuSECRET_KEY;
    }
    /**
     * 进行预标注
     * @param p_id 项目id
     * @return 返回完成情况
     */
    @GetMapping("/imgclas/{p_id}/{v_id}")
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
                    //将结果存入Results中，并在Dataall_result中进行记录
                    String jsonString = "{\"identify\":\""+bestEnd+"\"}";
                    String endName = bestEnd+".json";
                    MultipartFile multipartFile = fileUtil.createMultipartFile(endName,jsonString);
                    int r_id = dataall_ResultService.UploadResult("clasjson",multipartFile);
                    log.info("r_id= "+ r_id);
                    dataall_ResultService.insertOne(v_id,p_id,r_id);
                }
            }

        }
        //应该将bestEnd结果生成json文件，存入Results表中去
        return new Result(Code.GET_OK,"预标注已完成,共预标注"+i+"个数据",null);
    }

}
