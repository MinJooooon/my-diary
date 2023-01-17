package com.mydiary.common.model.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponseDto<T> {
    private String message;
    private T data;

    public ErrorResponseDto(String message) {
        this.message = message;
    }

    public ErrorResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
