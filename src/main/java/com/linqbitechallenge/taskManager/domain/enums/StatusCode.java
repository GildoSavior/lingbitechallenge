package com.linqbitechallenge.taskManager.domain.enums;


import lombok.Getter;

@Getter
public enum StatusCode {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

}
