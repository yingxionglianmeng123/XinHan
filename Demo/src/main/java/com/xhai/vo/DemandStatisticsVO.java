package com.xhai.vo;

import lombok.Data;

@Data
public class DemandStatisticsVO {
    private Long totalDemands;        // 总需求数
    private Long pendingDemands;      // 待处理需求数
    private Long inProgressDemands;   // 进行中需求数
    private Long completedDemands;    // 已完成需求数
    private Integer totalViews;       // 总浏览量
} 