package com.xhai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhai.entity.User;
import com.xhai.mapper.UserMapper;
import com.xhai.service.UserService;
import com.xhai.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Override
    public UserVO createUser(User user) {
        save(user);
        return convertToVO(user);
    }

    @Override
    public UserVO updateUser(User user) {
        updateById(user);
        return convertToVO(user);
    }

    @Override
    public UserVO getUserById(String id) {
        User user = getById(id);
        return user != null ? convertToVO(user) : null;
    }

    @Override
    public UserVO getUserByOpenid(String openid) {
        User user = baseMapper.selectByOpenid(openid);
        return user != null ? convertToVO(user) : null;
    }

    @Override
    public void deleteUser(String id) {
        removeById(id);
    }

    @Override
    public List<UserVO> getAllUsers() {
        List<User> users = list();
        return users.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
} 