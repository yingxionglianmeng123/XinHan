package com.xhai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("articles")
public class Article {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String cover;
    private String title;
    private Date date;
    private Integer views;
} 