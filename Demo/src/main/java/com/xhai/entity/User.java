package com.xhai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    private String openid;
    
    @TableField("user_type")
    private int userType;
    
    @TableField("nick_name")
    private String nickName;
    
    @TableField("avatar_url")
    private String avatarUrl;
    
    @TableField("real_name")
    private String realName;
    
    private String contact;
    
    private String company;
    
    @TableField("created_at")
    private LocalDateTime createdAt;
    
    @TableField("updated_at")
    private LocalDateTime updatedAt;
    
    private String skill;
} 