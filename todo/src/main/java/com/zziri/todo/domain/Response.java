package com.zziri.todo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response <T> {
    private String error;
    private T data;
}
