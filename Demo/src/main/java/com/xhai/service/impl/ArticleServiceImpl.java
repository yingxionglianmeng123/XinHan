package com.xhai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhai.entity.Article;
import com.xhai.mapper.ArticleMapper;
import com.xhai.service.ArticleService;
import com.xhai.vo.ArticleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public List<ArticleVO> getArticleList() {
        List<Article> articles = this.list();
        return articles.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleVO getArticleById(Integer id) {
        Article article = this.getById(id);
        return article != null ? convertToVO(article) : null;
    }

    @Override
    public void incrementViews(Integer id) {
        Article article = this.getById(id);
        if (article != null) {
            article.setViews(article.getViews() + 1);
            this.updateById(article);
        }
    }

    private ArticleVO convertToVO(Article article) {
        ArticleVO vo = new ArticleVO();
        BeanUtils.copyProperties(article, vo);
        return vo;
    }
} 