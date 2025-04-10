package com.xhai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xhai.entity.Article;
import com.xhai.vo.ArticleVO;

import java.util.List;

public interface ArticleService extends IService<Article> {
    List<ArticleVO> getArticleList();
    
    ArticleVO getArticleById(Integer id);
    
    void incrementViews(Integer id);
} 