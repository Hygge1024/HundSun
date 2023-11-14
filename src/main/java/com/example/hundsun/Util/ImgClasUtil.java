package com.example.hundsun.Util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Component
public class ImgClasUtil {
    /**
     * 图像识别分类 工具类
     */
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    /**
     * 调用百度api实现图片识别
     * @param imgUrl
     * @param API_KEY
     * @param SECRET_KEY
     * @return 返回的是5个值的String数组，里面是图片的所有可能值
     * @throws IOException
     */
    public String[] getImgClas(String imgUrl,String API_KEY, String SECRET_KEY) throws IOException {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "url="+imgUrl);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general?access_token=" + getAccessToken(API_KEY,SECRET_KEY))
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        if(response.isSuccessful()){
            ResponseBody responseBody = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody.byteStream());
            JsonNode resultNode = rootNode.get("result");
            System.out.println("result = "+resultNode);
//            resultNode是result中的内容，形式如：“{"keyword":"山地车","score":0.888501,"root":"商品-户外用品"}”
            int arraySize = resultNode.size();
            String[] keywords = new String[arraySize];
            for (int i = 0; i < arraySize; i++) {
                JsonNode keywordNode = resultNode.get(i).get("keyword");
                keywords[i] = keywordNode.asText();
            }
            // 记录结束时间
            long endTime = System.currentTimeMillis();
            // 计算经过的时间（以毫秒为单位）
            long elapsedTime = endTime - startTime;
            // 将毫秒转换为小时、分钟和秒
            long seconds = elapsedTime / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            // 计算剩余的分钟和秒
            minutes = minutes % 60;
            seconds = seconds % 60;
            System.out.println("经过的时间：" + hours + "小时 " + minutes + "分钟 " + seconds + "秒");
            return keywords;
        }
        return null;
    }

    /**
     * 获取令牌
     * @param API_KEY
     * @param SECRET_KEY
     * @return
     * @throws IOException
     */
    static String getAccessToken(String API_KEY, String SECRET_KEY) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }

    /**
     * 自动识别的类别与标签对比，得到对应的标签类别
     * @param targets
     * @param clasends
     * @return 返回图片对应标签列表中的标签值
     */
    public String getCompare(String[] targets,String[] clasends){
        double bestCompareScore = 0.0;
        String bestLabel = null;
        for(String clasend : clasends){
            for(String target: targets){
                double similarity = calculateCosineSimilarity(clasend,target);
                if(similarity > bestCompareScore){
                    bestCompareScore = similarity;
                    bestLabel = clasend;
                }
            }
        }
        return bestLabel;
    }

    /**
     *计算两个文本之间的相似度
     * @param strA 业务人员定义标签
     * @param strB 自动识别的5种可能标签
     * @return 相似度值double类型
     */
    public static double calculateCosineSimilarity(String strA, String strB) {
        //考虑的是集合之间的关系，因此使用杰拉德JaccardSimilarity
        // 将字符串转换为字符集合
        Set<Character> setA = new HashSet<>();
        for (char c : strA.toCharArray()) {
            setA.add(c);
        }
        Set<Character> setB = new HashSet<>();
        for (char c : strB.toCharArray()) {
            setB.add(c);
        }
        // 计算交集和并集的大小
        Set<Character> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        Set<Character> union = new HashSet<>(setA);
        union.addAll(setB);
        // 计算Jaccard相似度
        double result = (double) intersection.size() / union.size();
        // 格式化返回值为8位小数点
        DecimalFormat decimalFormat = new DecimalFormat("#.########");
        String formattedResult = decimalFormat.format(result);
        return Double.parseDouble(formattedResult);
    }
}
