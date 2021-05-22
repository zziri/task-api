package com.zziri.todo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GoogleProfile implements SocialProfile {
    private String id;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String email;
    private Boolean emailVerified;
    private String locale;

    @Override
    public String getAccount() {
        return id;
    }
}
