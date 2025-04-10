package com.xhai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhai.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
} 