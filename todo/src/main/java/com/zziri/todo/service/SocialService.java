package com.zziri.todo.service;

import com.zziri.todo.domain.OAuthTokenInfo;
import com.zziri.todo.domain.SocialProfile;

public interface SocialService {
    SocialProfile getProfile(String accessToken);
    OAuthTokenInfo getTokenInfo(String code);
    String getType();
}
