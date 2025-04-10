package com.xhai.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface MinioService {
    /**
     * 上传文件
     * @param file 文件
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file) throws Exception;

    /**
     * 上传文件
     * @param file 文件
     * @param fileName 文件名
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String fileName) throws Exception;

    /**
     * 批量上传文件
     * @param files 文件列表
     * @return 文件访问URL列表
     */
    List<String> uploadFiles(List<MultipartFile> files) throws Exception;

    /**
     * 删除文件
     * @param fileName 文件名
     */
    void deleteFile(String fileName) throws Exception;

    /**
     * 获取文件
     * @param fileName 文件名
     * @return 文件流
     */
    InputStream getFile(String fileName) throws Exception;

    /**
     * 获取文件URL
     * @param fileName 文件名
     * @return 文件URL
     */
    String getFileUrl(String fileName) throws Exception;

    /**
     * 获取文件预览URL
     * @param fileName 文件名
     * @return 文件预览URL
     */
    String getPreviewUrl(String fileName) throws Exception;
} 