package com.zziri.todo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile implements SocialProfile {
    private Long id;
    private Properties properties;

    @Override
    public String getAccount() {
        return id.toString();
    }

    @Override
    public String getName() {
        return properties.getName();
    }

    @Getter
    @Setter
    @ToString
    private static class Properties {
        private String name;
        private String thumbnail_image;
        private String profile_image;
    }
}
