package com.example.hundsun.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.hundsun.Service.Data_allService;
import com.example.hundsun.Service.ResultsService;
import com.example.hundsun.Service.dataall_resultService;
import com.example.hundsun.Util.ResultUtil.Code;
import com.example.hundsun.Util.ResultUtil.Result;
import com.example.hundsun.domain.Data_all;
import com.example.hundsun.domain.Results;
import com.example.hundsun.domain.self.CompositeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 标注人员的Controller
 */
@RestController
@RequestMapping("/api/Annotation")
@Slf4j
public class AnnotationController {
    @Autowired
    private Data_allService dataAllService;
    @Autowired
    private dataall_resultService dataallResultService;
    @Autowired
    private ResultsService resultsService;
    /**
     * 标注人员获取 要标注的数据+预标注结果
     * @param p_id
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/get/{p_id}/{v_id}/{currentPage}/{pageSize}")
    public Result getData_PreResult(@PathVariable int p_id,@PathVariable int v_id,@PathVariable int currentPage,@PathVariable int pageSize){
        //现获取dataall中页数里面的数据
        IPage page = dataAllService.getPageByP_id(p_id,currentPage,pageSize);
        if(currentPage > page.getPages()){
            page = dataAllService.getPageByP_id(p_id,(int) page.getPages(), pageSize);
        }
        List<Data_all> data_allList = page.getRecords();
        List<CompositeData> compositeDataList = new ArrayList<>();
        //dataall->did + vid通过dataall_result查找对应的结果
        for(Data_all dataOne:data_allList){
            int r_id = dataallResultService.getByPidVid(dataOne.getId(),v_id);
            Results results = resultsService.getByID(r_id);
            CompositeData compositeData = new CompositeData();
            compositeData.setDataAll(dataOne);
            compositeData.setResults(results);
            compositeDataList.add(compositeData);
        }
        Integer code = compositeDataList!= null ? Code.GET_OK:Code.GET_ERR;
        String msg = compositeDataList !=null?"获取原始数据+预标注结果成功":"获取数据识别";
        return new Result(code,msg,compositeDataList);
    }
}
