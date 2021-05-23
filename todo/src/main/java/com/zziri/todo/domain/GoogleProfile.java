package com.zziri.todo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GoogleProfile implements SocialProfile {
    private String sub;
    private String name;
    private String email;
    private String locale;

    @Override
    public String getAccount() {
        return sub;
    }
}
