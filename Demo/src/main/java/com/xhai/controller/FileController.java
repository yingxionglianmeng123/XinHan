package com.xhai.controller;

import com.xhai.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFile(file);
    }

    @DeleteMapping("/{fileName}")
    public void deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
    }

    @GetMapping("/{fileName}")
    public String getFileUrl(@PathVariable String fileName) {
        return fileService.getFileUrl(fileName);
    }
} 