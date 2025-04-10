package com.xhai.dto;

import lombok.Data;

@Data
public class WxLoginDTO {
    private String code;
    private String rawData;
    private String signature;
    private String nickName;
    private String avatarUrl;
    private String gender;
    private String country;
    private String province;
    private String city;
    private String language;
    private String realName;
    private String contact;
    private String company;
    private String skill;
} 