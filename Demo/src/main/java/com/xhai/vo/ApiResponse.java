package com.xhai.vo;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> error(ResultCode resultCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(resultCode.getCode());
        response.setMessage(resultCode.getMessage());
        return response;
    }

    public static <T> ApiResponse<T> error(ResultCode resultCode, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(resultCode.getCode());
        response.setMessage(message);
        return response;
    }
} 