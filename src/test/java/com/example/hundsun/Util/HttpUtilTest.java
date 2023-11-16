package com.example.hundsun.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringEscapeUtils;

import org.json.JSONObject;

import java.io.IOException;

@SpringBootTest
public class HttpUtilTest {
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private MinIoUtil minIoUtil;
    @Test
    void processJsonAndUpload() throws JSONException, IOException {

        JSONObject requestJson = new JSONObject();
        requestJson.put("doc_url", "http://8.137.53.253:9000/txtclassify/2112190422_%E8%BD%AF%E4%BB%B62102_%E6%96%BD%E8%80%80%E7%BF%94.docx");
        JSONArray labelsArray = new JSONArray();
        labelsArray.put("学校名称");
        labelsArray.put("时间");
        labelsArray.put("地点");
        labelsArray.put("人物名称");
        requestJson.put("labels", labelsArray);

//        System.out.println(requestJson);
        String responseJsonString = httpUtil.sendHttpPostRequest("http://127.0.0.1:8666/pre/docentity", requestJson);
        System.out.println(responseJsonString);
        // 对responseJsonString进行Unicode解码
        String decodedResponse = StringEscapeUtils.unescapeJava(responseJsonString);
        System.out.println(decodedResponse);

//        JSONObject jsonResponse = new JSONObject(responseJsonString);

        String fileName = "response.json";
//        httpUtil.writeJsonToFile(jsonResponse, fileName);
        MultipartFile multipartFile = FileUtil.createMultipartFile(fileName, decodedResponse);
//        System.out.println(multipartFile.getBytes());
//        System.out.println(multipartFile.getName());
//        System.out.println(multipartFile.getContentType());
        String bucketName = "txtclassify";
        minIoUtil.upload(multipartFile,bucketName);


    }
}
