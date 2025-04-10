package com.xhai.controller;

import com.xhai.entity.Article;
import com.xhai.service.ArticleService;
import com.xhai.service.MinioService;
import com.xhai.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MinioService minioService;

    @GetMapping
    public Map<String, Object> getArticleList() {
        List<ArticleVO> articleList = articleService.getArticleList();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "success");
        Map<String, Object> data = new HashMap<>();
        data.put("articleList", articleList);
        response.put("data", data);
        return response;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getArticle(@PathVariable Integer id) {
        ArticleVO article = articleService.getArticleById(id);
        Map<String, Object> response = new HashMap<>();
        if (article != null) {
            articleService.incrementViews(id);
            response.put("code", 200);
            response.put("message", "success");
            response.put("data", article);
        } else {
            response.put("code", 404);
            response.put("message", "文章不存在");
        }
        return response;
    }

    @PostMapping
    public Map<String, Object> createArticle(
            @RequestParam("title") String title,
            @RequestParam("cover") MultipartFile coverFile) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 上传封面图片到MinIO
            String coverUrl = minioService.uploadFile(coverFile);
            
            // 创建文章
            Article article = new Article();
            article.setTitle(title);
            article.setCover(coverUrl);
            article.setDate(new Date());
            article.setViews(0);
            
            articleService.save(article);
            
            response.put("code", 200);
            response.put("message", "success");
            response.put("data", articleService.getArticleById(article.getId()));
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "创建文章失败：" + e.getMessage());
        }
        return response;
    }

    @PutMapping("/{id}/cover")
    public Map<String, Object> updateArticleCover(
            @PathVariable Integer id,
            @RequestParam("cover") MultipartFile coverFile) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 检查文章是否存在
            Article article = articleService.getById(id);
            if (article == null) {
                response.put("code", 404);
                response.put("message", "文章不存在");
                return response;
            }

            // 上传新图片到MinIO
            String newCoverUrl = minioService.uploadFile(coverFile);
            
            // 更新文章封面
            article.setCover(newCoverUrl);
            articleService.updateById(article);
            
            response.put("code", 200);
            response.put("message", "success");
            response.put("data", articleService.getArticleById(id));
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "更新文章封面失败：" + e.getMessage());
        }
        return response;
    }
} 