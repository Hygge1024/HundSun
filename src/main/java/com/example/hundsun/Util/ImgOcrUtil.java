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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
@Component
public class ImgOcrUtil {
    /**
     * 调用OCR服务器，识别图像的文本及坐标信息
     * @param cls
     * @param imgUrl
     * @param targetUrl
     * @return
     */
    public String getClass(Integer cls,String imgUrl,String targetUrl){
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == 200) {
                // 从URL获取图像数据
                InputStream is = connection.getInputStream();
//                byte[] image_data = is.readAllBytes();//Java9
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];

                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }

                buffer.flush();
                byte[] image_data = buffer.toByteArray();
                is.close();

                // 将图像数据转换为Base64编码
                String base64_image = Base64.getEncoder().encodeToString(image_data);
//                System.out.println(base64_image);
                //发送请求部分
                HttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(targetUrl);
                try {
                    String json = "{\"images\": [\""+base64_image+"\"]}";
                    StringEntity entity = new StringEntity(json);
//                    entity.setContentType("application/json; charset=utf-8");
                    httpPost.setEntity(entity);
                    httpPost.setHeader("Content-type", "application/json");
                    //发送请求并处理
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity responseEntity = response.getEntity();
//                    System.out.println(responseEntity);

                    if (response.getStatusLine().getStatusCode() == 200) {
                        String responseString = EntityUtils.toString(responseEntity);
                        // 对返回的JSON字符串进行反转义
                        String unescapedResponse = StringEscapeUtils.unescapeJava(responseString);
//                        System.out.println("返回的内容为："+unescapedResponse);

                        if (cls == 2){ //如果是ocr-将进行格式转
                            JSONObject jsonObject = new JSONObject(unescapedResponse);
                            JSONArray resultsArray = jsonObject.getJSONArray("results");
                            JSONArray newDataArray = resultsArray.getJSONObject(0).getJSONArray("data");
                            // 创建新的格式
                            JSONArray newFormatArray = new JSONArray();
                            for (int i = 0; i < newDataArray.length(); i++) {
                                JSONObject dataObject = newDataArray.getJSONObject(i);
                                double confidence = dataObject.getDouble("confidence");
                                String text = dataObject.getString("text");
                                JSONArray text_box_position = dataObject.getJSONArray("text_box_position");

                                // 构建新格式的JSON对象
                                JSONArray coordinates = new JSONArray();
                                for (int j = 0; j < text_box_position.length(); j++) {
                                    JSONArray point = text_box_position.getJSONArray(j);
                                    JSONObject coordinate = new JSONObject();
                                    coordinate.put("x", point.getInt(0));
                                    coordinate.put("y", point.getInt(1));
                                    coordinates.put(coordinate);
                                }

                                JSONObject newFormatObject = new JSONObject();
                                newFormatObject.put("坐标框", coordinates);
                                newFormatObject.put("文本内容", text);
                                newFormatObject.put("置信度", confidence);

                                // 添加到新格式的数组中
                                newFormatArray.put(newFormatObject);
//                                System.out.println(newFormatArray.toString(4)); // 使用缩进格式化输出
                            }
                            return newFormatArray.toString();
                        }else{
                            return unescapedResponse;
                        }
//                        System.out.println(unescapedResponse);
                    } else {
                        System.out.println("Failed to send POST request.");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Failed to retrieve the image from the URL.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
