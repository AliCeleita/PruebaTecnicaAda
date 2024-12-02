package com.ada.error;

import lombok.Getter;

@Getter
public enum Errors {

    NO_EXISTE("Este producto no existe");

    private final String message;

    Errors(String message) {
        this.message = message;
    }

}
