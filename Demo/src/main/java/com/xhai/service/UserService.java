package com.xhai.service;

import com.xhai.entity.User;
import com.xhai.vo.UserVO;
import java.util.List;

public interface UserService {
    UserVO createUser(User user);
    UserVO updateUser(User user);
    UserVO getUserById(String id);
    UserVO getUserByOpenid(String openid);
    void deleteUser(String id);
    List<UserVO> getAllUsers();
} 