package com.xhai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhai.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM users WHERE openid = #{openid}")
    User selectByOpenid(String openid);
} 