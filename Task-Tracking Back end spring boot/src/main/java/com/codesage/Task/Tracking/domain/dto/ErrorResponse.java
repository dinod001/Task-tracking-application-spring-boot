package com.codesage.Task.Tracking.domain.dto;

public record ErrorResponse (
        int status,
        String message,
        String details
){
}
