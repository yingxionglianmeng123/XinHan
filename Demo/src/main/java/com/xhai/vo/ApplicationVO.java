package com.xhai.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ApplicationVO {
    private String id;
    private String demandId;
    private String applicantId;
    private String proposal;
    private Double bidPrice;
    private String bidDuration;
    private String status;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserVO applicant; // 申请者信息
    private DemandVO demand; // 需求信息
} 