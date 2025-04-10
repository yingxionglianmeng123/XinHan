package com.xhai.vo;

import lombok.Data;

@Data
public class DemandStatisticsVO {
    private Long totalDemands;      // 总需求数
    private Long pendingDemands;    // 待处理需求
    private Long inProgressDemands; // 进行中需求
    private Long completedDemands;  // 已完成需求
    private Double walletBalance;   // 钱包余额
} 