package com.xhai.service;

import com.xhai.dto.WxLoginDTO;
import com.xhai.dto.WxLoginResponseDTO;

public interface WxLoginService {
    WxLoginResponseDTO login(WxLoginDTO loginDTO);
    String verifyToken(String token);
} 