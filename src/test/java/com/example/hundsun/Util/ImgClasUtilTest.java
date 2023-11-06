package com.example.hundsun.Util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
@Slf4j
@SpringBootTest
public class ImgClasUtilTest {
    @Autowired
    private ImgClasUtil imgClasUtil;
    @Test
    void clas01() throws IOException {
        String imgurl = "https://img.zcool.cn/community/01081b5bcf2274a8012099c832b3ca.jpg@1280w_1l_2o_100sh.jpg";
        String APIkey = "dRzeeE2kKkC10kb5WrLj0CFC";
        String Secretkey = "Gy2gBGX2UNgo1Q89xbnL3Z1AdMlgNQkB";
        String[] ends = imgClasUtil.getImgClas(imgurl,APIkey,Secretkey);
        for(String end : ends){
            System.out.println(end);
        }
    }
    @Test
    void getCompareScore(){
        String[] a = {"山地车","折叠自行车","脚踏车","折叠车","摩托车"};
        String[] b = {"飞机","自行车","脚踏车"};
        log.info("很好拉");
        System.out.println(imgClasUtil.getCompare(a,b));
//        System.out.println(imgClasUtil.calculateCosineSimilarity("披萨","纽约式披萨"));
//        System.out.println(imgClasUtil.calculateCosineSimilarity("自行车","脚踏车"));
    }
}
