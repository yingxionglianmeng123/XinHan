package com.xhai.controller;

import com.xhai.entity.User;
import com.xhai.service.UserService;
import com.xhai.vo.ApiResponse;
import com.xhai.vo.UserVO;
import com.xhai.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserVO>> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(ApiResponse.success(userService.createUser(user)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<UserVO>> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            user.setId(id);
            return ResponseEntity.ok(ApiResponse.success(userService.updateUser(user)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<UserVO>> getUser(@PathVariable String id) {
        UserVO user = userService.getUserById(id);
        return user != null ? 
            ResponseEntity.ok(ApiResponse.success(user)) : 
            ResponseEntity.ok(ApiResponse.error(ResultCode.NOT_FOUND, "用户不存在"));
    }

    @GetMapping("/openid/{openid}")
    public ResponseEntity<ApiResponse<UserVO>> getUserByOpenid(@PathVariable String openid) {
        UserVO user = userService.getUserByOpenid(openid);
        return user != null ? 
            ResponseEntity.ok(ApiResponse.success(user)) : 
            ResponseEntity.ok(ApiResponse.error(ResultCode.NOT_FOUND, "用户不存在"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserVO>>> getAllUsers() {
        try {
            return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(ResultCode.INTERNAL_ERROR, e.getMessage()));
        }
    }
} 