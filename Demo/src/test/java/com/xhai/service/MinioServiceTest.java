package com.xhai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MinioServiceTest {

    @Autowired
    private MinioService minioService;

    @Test
    public void testUploadAndDeleteFile() throws IOException {
        // 创建测试文件
        String content = "测试文件内容";
        MultipartFile file = new MockMultipartFile(
                "test.txt",
                "test.txt",
                "text/plain",
                content.getBytes(StandardCharsets.UTF_8)
        );

        // 上传文件
        String fileUrl = minioService.uploadFile(file);
        assertNotNull(fileUrl);
        assertTrue(fileUrl.contains("test.txt"));

        // 获取文件URL
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String retrievedUrl = minioService.getFileUrl(fileName);
        assertEquals(fileUrl, retrievedUrl);

        // 删除文件
        minioService.deleteFile(fileName);
    }

    @Test
    public void testUploadWithCustomName() throws IOException {
        // 创建测试文件
        String content = "自定义文件名测试";
        MultipartFile file = new MockMultipartFile(
                "original.txt",
                "original.txt",
                "text/plain",
                content.getBytes(StandardCharsets.UTF_8)
        );

        // 使用自定义文件名上传
        String customFileName = "custom-name.txt";
        String fileUrl = minioService.uploadFile(file, customFileName);
        assertNotNull(fileUrl);
        assertTrue(fileUrl.contains(customFileName));

        // 清理
        minioService.deleteFile(customFileName);
    }
} 