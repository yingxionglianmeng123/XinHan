package com.xhai.service.impl;

import com.xhai.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.HashMap;
import java.util.Arrays;

@Service
public class MinioServiceImpl implements MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.max-file-size:10485760}") // 默认10MB
    private long maxFileSize;

    @Value("${minio.allowed-file-types:image/jpeg,image/png,image/gif,application/pdf}")
    private String allowedFileTypes;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        validateFile(file);
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        return uploadFile(file, fileName);
    }

    @Override
    public String uploadFile(MultipartFile file, String fileName) throws Exception {
        validateFile(file);
        
        // 检查存储桶是否存在，不存在则创建
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // 上传文件
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());

        return endpoint + "/" + bucketName + "/" + fileName;
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> files) throws Exception {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = uploadFile(file);
            urls.add(url);
        }
        return urls;
    }

    @Override
    public void deleteFile(String fileName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
    }

    @Override
    public InputStream getFile(String fileName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
    }

    @Override
    public String getFileUrl(String fileName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
    }

    @Override
    public String getPreviewUrl(String fileName) throws Exception {
        // 获取文件信息
        StatObjectResponse stat = minioClient.statObject(
                StatObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );

        // 如果是图片或PDF，生成预览URL
        if (stat.contentType().startsWith("image/") || stat.contentType().equals("application/pdf")) {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        }
        
        // 其他类型文件返回下载URL
        return getFileUrl(fileName);
    }

    private void validateFile(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("文件不能为空");
        }

        if (file.getSize() > maxFileSize) {
            throw new Exception("文件大小超过限制");
        }

        String contentType = file.getContentType();
        if (contentType == null || !Arrays.asList(allowedFileTypes.split(",")).contains(contentType)) {
            throw new Exception("不支持的文件类型");
        }
    }
} 