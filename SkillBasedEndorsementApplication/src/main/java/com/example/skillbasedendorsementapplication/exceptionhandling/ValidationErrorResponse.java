package com.example.skillbasedendorsementapplication.exceptionhandling;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ValidationErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}
