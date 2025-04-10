package com.xhai.dto;

import lombok.Data;

@Data
public class WxLoginResponseDTO {
    private String token;
    private String openid;
    private int userType;
    private String nickName;
    private String avatarUrl;
} 