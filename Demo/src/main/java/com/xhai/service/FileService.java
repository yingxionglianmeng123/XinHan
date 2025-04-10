package com.xhai.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);
    void deleteFile(String fileName);
    String getFileUrl(String fileName);
} 