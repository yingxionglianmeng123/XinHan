package com.xhai.vo;

import lombok.Data;
import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> list;
    private Long total;

    public static <T> PageResponse<T> of(List<T> list, Long total) {
        PageResponse<T> response = new PageResponse<>();
        response.setList(list);
        response.setTotal(total);
        return response;
    }
} 