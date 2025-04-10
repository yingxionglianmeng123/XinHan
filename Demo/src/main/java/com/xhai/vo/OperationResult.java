package com.xhai.vo;

import lombok.Data;

@Data
public class OperationResult {
    private boolean success;

    public static OperationResult success() {
        OperationResult result = new OperationResult();
        result.setSuccess(true);
        return result;
    }

    public static OperationResult error() {
        OperationResult result = new OperationResult();
        result.setSuccess(false);
        return result;
    }
} 