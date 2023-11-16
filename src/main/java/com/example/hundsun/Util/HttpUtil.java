package com.example.hundsun.Util;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class HttpUtil {

    public String sendHttpPostRequest(String url, JSONObject requestJson) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {
            StringEntity entity = new StringEntity(requestJson.toString(), StandardCharsets.UTF_8); // 指定字符编码为UTF-8
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8"); // 设置请求头的Content-Type

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                return EntityUtils.toString(responseEntity, StandardCharsets.UTF_8); // 指定字符编码为UTF-8
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}