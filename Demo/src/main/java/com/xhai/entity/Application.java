package com.xhai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("applications")
public class Application {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    
    @TableField("demand_id")
    private String demandId;
    
    @TableField("applicant_id")
    private String applicantId;
    
    private String proposal;
    
    @TableField("bid_price")
    private Double bidPrice;
    
    @TableField("bid_duration")
    private String bidDuration;
    
    private String status;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
} 