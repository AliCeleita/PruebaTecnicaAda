package com.ada.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorDto {

    private int statusCode;
    private String message;
    private String details;

    private ErrorDto() {}
}
