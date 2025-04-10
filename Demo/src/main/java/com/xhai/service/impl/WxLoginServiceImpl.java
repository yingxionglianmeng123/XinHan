package com.xhai.service.impl;

import com.xhai.config.WxConfig;
import com.xhai.dto.WxLoginDTO;
import com.xhai.dto.WxLoginResponseDTO;
import com.xhai.entity.User;
import com.xhai.mapper.UserMapper;
import com.xhai.service.WxLoginService;
import com.xhai.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service
public class WxLoginServiceImpl implements WxLoginService {

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public WxLoginResponseDTO login(WxLoginDTO loginDTO) {
        try {
            // 1. 调用微信接口获取openid和session_key
            String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                    wxConfig.getAppid(), wxConfig.getSecret(), loginDTO.getCode());

            
            log.info("调用微信登录接口: {}", url);
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> result = response.getBody();
            
            if (result == null) {
                log.error("微信登录失败: 返回结果为空");
                throw new RuntimeException("微信登录失败");
            }

            if (result.containsKey("errcode")) {
                Integer errcode = (Integer) result.get("errcode");
                String errmsg = (String) result.get("errmsg");
                log.error("微信登录失败: errcode={}, errmsg={}", errcode, errmsg);
                throw new RuntimeException("微信登录失败: " + errmsg);
            }

            String openid = (String) result.get("openid");
            String sessionKey = (String) result.get("session_key");

            if (openid == null || sessionKey == null) {
                log.error("微信登录失败: openid或session_key为空");
                throw new RuntimeException("微信登录失败: 获取用户信息失败");
            }

            // 2. 查找或创建用户
            User user = userMapper.selectByOpenid(openid);
            if (user == null) {
                user = new User();
                // 设置基本信息
                user.setOpenid(openid);
                user.setUserType(2); // 默认用户类型
                user.setNickName(loginDTO.getNickName());
                user.setAvatarUrl(loginDTO.getAvatarUrl());
                // 设置其他信息
                user.setRealName(loginDTO.getRealName());
                user.setContact(loginDTO.getContact());
                user.setCompany(loginDTO.getCompany());
                user.setSkill(loginDTO.getSkill());
                // 设置时间
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                
                userMapper.insert(user);
                log.info("创建新用户: openid={}, nickName={}, realName={}, contact={}, company={}, skill={}, createdAt={}", 
                    openid, loginDTO.getNickName(), loginDTO.getRealName(), 
                    loginDTO.getContact(), loginDTO.getCompany(), loginDTO.getSkill(), 
                    user.getCreatedAt());
            } else {
                // 更新用户信息
                user.setNickName(loginDTO.getNickName());
                user.setAvatarUrl(loginDTO.getAvatarUrl());
                user.setRealName(loginDTO.getRealName());
                user.setContact(loginDTO.getContact());
                user.setCompany(loginDTO.getCompany());
                user.setSkill(loginDTO.getSkill());
                user.setUpdatedAt(LocalDateTime.now()); // 更新修改时间
                userMapper.updateById(user);
                log.info("更新用户信息: openid={}, nickName={}, realName={}, contact={}, company={}, skill={}, updatedAt={}", 
                    openid, loginDTO.getNickName(), loginDTO.getRealName(), 
                    loginDTO.getContact(), loginDTO.getCompany(), loginDTO.getSkill(), 
                    user.getUpdatedAt());
            }

            // 3. 生成token
            String token = jwtUtil.generateToken(user.getId());

            // 4. 构建响应
            WxLoginResponseDTO responseDTO = new WxLoginResponseDTO();
            responseDTO.setToken(token);
            responseDTO.setOpenid(openid);
            responseDTO.setUserType(user.getUserType());
            responseDTO.setNickName(user.getNickName());
            responseDTO.setAvatarUrl(user.getAvatarUrl());

            return responseDTO;
        } catch (Exception e) {
            log.error("微信登录异常", e);
            throw new RuntimeException("微信登录失败: " + e.getMessage());
        }
    }

    @Override
    public String verifyToken(String token) {
        return jwtUtil.verifyToken(token);
    }
} 