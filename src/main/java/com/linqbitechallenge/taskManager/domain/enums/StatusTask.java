package com.linqbitechallenge.taskManager.domain.enums;

import lombok.Getter;

@Getter
public enum StatusTask {
    PENDENTE(0),
    EM_PROGRESSO(1),
    CONCLUIDA(2);

    private final int code;

    StatusTask(int code) {
        this.code = code;
    }
}
