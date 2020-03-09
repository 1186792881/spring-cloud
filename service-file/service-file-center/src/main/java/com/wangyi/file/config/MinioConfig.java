package com.wangyi.file.config;

import com.wangyi.file.util.MinioFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    @Value("${minio.bucketName}")
    private String bucketName;

    @Bean
    public MinioFileUtil minioFileUtil() {
        return new MinioFileUtil(endpoint, accessKey, secretKey, bucketName);
    }

}
