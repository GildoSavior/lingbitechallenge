package com.linqbitechallenge.taskManager.application.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private String message;
    private boolean ok;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>("Successo", true, data);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(message, false, null);
    }
}
