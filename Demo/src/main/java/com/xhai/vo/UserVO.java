package com.xhai.vo;

import lombok.Data;
import java.util.List;

@Data
public class UserVO {
    private Long id;
    private String userType;
    private String nickName;
    private String avatarUrl;
    private String realName;
    private String contact;
    private List<String> skills;
    private String company;
} 