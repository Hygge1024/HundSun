package com.example.hundsun.Util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImgOcrUtilTest {
    @Autowired
    private ImgOcrUtil imgOcrUtil;
    //	模型调用测试
    @Test
    void getModel(){
        String imgocr_4 = "http://192.168.204.130:9000/imgocr/img_4.png";
        String ch_pp_ocrv3= "http://192.168.204.130:8866/predict/ch_pp_ocrv3";
	String ends = imgOcrUtil.getClass(2,imgocr_4,ch_pp_ocrv3);
	System.out.println(ends);

        String imgclas_1 = "http://192.168.204.130:9000/imgclas/img_1.png";
        String resnet50_vd_animals= "http://192.168.204.130:8866/predict/resnet50_vd_animals";
//	String ends = imgOcrUtil.getClass(1,imgclas_1,resnet50_vd_animals);
//	System.out.println(ends);

        String imgclas_4 = "http://192.168.204.130:9000/imgclas/jzg.jpg";
        String mobilenet_v2_dishes= "http://192.168.204.130:8866/predict/mobilenet_v2_dishes";
//        String ends = imgOcrUtil.getClass(0,imgclas_4,mobilenet_v2_dishes);
//        System.out.println(ends);
    }
}
