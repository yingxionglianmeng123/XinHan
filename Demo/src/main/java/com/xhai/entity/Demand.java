package com.xhai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("demands")
public class Demand {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private String title;
    
    private String description;
    
    private BigDecimal price;
    
    private String category;
    
    @TableField(typeHandler = com.xhai.handler.JsonTypeHandler.class)
    private List<String> attachments;
    
    private LocalDate deadline;
    
    private String status;
    
    @TableField("publisherId")
    private String publisherId;
    
    @TableField("viewCount")
    private Integer viewCount = 0;
    
    @TableField("createdAt")
    private LocalDateTime createdAt;
    
    @TableField("deleted")
    private Integer deleted = 0;
} 