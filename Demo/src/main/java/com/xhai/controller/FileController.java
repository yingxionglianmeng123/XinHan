package com.xhai.controller;

import com.xhai.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return minioService.uploadFile(file);
    }

    @PostMapping("/upload/batch")
    public List<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        return minioService.uploadFiles(files);
    }

    @DeleteMapping("/{fileName}")
    public void deleteFile(@PathVariable String fileName) {
        minioService.deleteFile(fileName);
    }

    @GetMapping("/{fileName}")
    public String getFileUrl(@PathVariable String fileName) {
        return minioService.getFileUrl(fileName);
    }
} 