package com.wangyi.file.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

@Slf4j
public class MinioFileUtil {

    private String bucketName;
    private MinioClient minioClient;

    /**
     * Minio 上传工具初始化
     * @param endpoint
     * @param accessKey
     * @param secretKey
     * @param bucketName
     */
    public MinioFileUtil(String endpoint, String accessKey, String secretKey, String bucketName) {
        try {
            this.bucketName = bucketName;
            this.minioClient = new MinioClient(endpoint, accessKey, secretKey);
        } catch (Exception e) {
            log.error("初始化MinoFileUtil失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件
     *
     * @param inputStream
     * @param extName
     * @return 文件访问路径
     */
    public String upload(InputStream inputStream, String extName) {
        try {
            String objectName = DateUtil.format(new Date(), "yyyy/MM/dd") + "/" + IdUtil.simpleUUID() + "." + extName;
            minioClient.putObject(bucketName, objectName, inputStream, "application/octet-stream");
            return minioClient.getObjectUrl(bucketName, objectName);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        MinioFileUtil minioFileUtil = new MinioFileUtil("http://127.0.0.1:9000", "minioadmin", "minioadmin", "test");
        String fileName = "E:\\repos\\io\\minio\\minio\\3.0.10\\minio-3.0.10.jar";
        String url = minioFileUtil.upload(new FileInputStream(fileName), FileUtil.extName(fileName));
        System.out.println(url);
    }

}
