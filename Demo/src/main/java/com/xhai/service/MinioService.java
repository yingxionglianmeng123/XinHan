package com.xhai.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface MinioService {
    /**
     * 上传文件
     * @param file 文件
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file);

    /**
     * 上传文件
     * @param file 文件
     * @param fileName 文件名
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String fileName);

    /**
     * 批量上传文件
     * @param files 文件列表
     * @return 文件访问URL列表
     */
    List<String> uploadFiles(List<MultipartFile> files);

    /**
     * 删除文件
     * @param fileName 文件名
     */
    void deleteFile(String fileName);

    /**
     * 获取文件
     * @param fileName 文件名
     * @return 文件流
     */
    InputStream getFile(String fileName);

    /**
     * 获取文件URL
     * @param fileName 文件名
     * @return 文件URL
     */
    String getFileUrl(String fileName);
} 