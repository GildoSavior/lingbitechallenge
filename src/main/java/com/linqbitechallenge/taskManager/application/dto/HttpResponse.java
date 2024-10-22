package com.linqbitechallenge.taskManager.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse<T> {
    private String message;
    private int httpStatusCode;
    private T data;
}