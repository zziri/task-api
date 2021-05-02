package com.zziri.todo.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class ErrorDto {
    private int code;
    private String message;
}
