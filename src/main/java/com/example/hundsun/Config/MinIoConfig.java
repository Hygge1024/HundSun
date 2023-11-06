package com.example.hundsun.Config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinIoConfig {
    private String endpoint;//url,MinIo服务器地址
    private String accessKey;
    private String secretKey;
    private String bucketName;//Bucket名

    @Bean
    public MinioClient minioClient(){
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(endpoint)
                        .credentials(accessKey,secretKey)
                        .build();
        return minioClient;
    }
}
