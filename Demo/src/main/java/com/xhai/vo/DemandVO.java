package com.xhai.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DemandVO {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private List<String> attachments;
    private LocalDate deadline;
    private String status;
    private PublisherVO publisher;
    private Integer viewCount;
    private LocalDateTime createdAt;
}