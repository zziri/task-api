package com.zziri.task.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response <T> {
    private boolean error;
    private T data;
}
