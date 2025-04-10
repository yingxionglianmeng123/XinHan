package com.xhai.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleVO {
    private Integer id;
    private String cover;
    private String title;
    private Date date;
    private Integer views;
} 