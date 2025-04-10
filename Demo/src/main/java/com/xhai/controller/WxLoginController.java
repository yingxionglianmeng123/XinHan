package com.xhai.controller;

import com.xhai.dto.WxLoginDTO;
import com.xhai.dto.WxLoginResponseDTO;
import com.xhai.service.WxLoginService;
import com.xhai.vo.ApiResponse;
import com.xhai.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wx")
public class WxLoginController {

    @Autowired
    private WxLoginService wxLoginService;

    @PostMapping("/login")  //获取前端传入的code，然后调用微信登录接口，获取openid和session_key
    public ResponseEntity<ApiResponse<WxLoginResponseDTO>> login(@RequestBody WxLoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(ApiResponse.success(wxLoginService.login(loginDTO)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
} 