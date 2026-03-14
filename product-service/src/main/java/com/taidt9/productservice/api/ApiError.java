package com.taidt9.productservice.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {

    private String code;
    private String message;
    private Object details;
}