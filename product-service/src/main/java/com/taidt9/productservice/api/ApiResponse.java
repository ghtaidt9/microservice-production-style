package com.taidt9.productservice.api;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private Object error;
    private LocalDateTime timestamp;
}
