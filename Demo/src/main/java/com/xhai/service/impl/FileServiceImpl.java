package com.xhai.service.impl;

import com.xhai.config.MinioConfig;
import com.xhai.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // 确保存储桶存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .build());
            
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .build());
            }

            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;

            // 上传文件
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            return fileName;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            log.error("文件删除失败", e);
            throw new RuntimeException("文件删除失败: " + e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .method(Method.GET)
                    .build());
        } catch (Exception e) {
            log.error("获取文件URL失败", e);
            throw new RuntimeException("获取文件URL失败: " + e.getMessage());
        }
    }
} 