package com.zziri.task.service;

import com.zziri.task.domain.OAuthTokenInfo;
import com.zziri.task.domain.SocialProfile;

public interface SocialService {
    SocialProfile getProfile(String accessToken);
    OAuthTokenInfo getTokenInfo(String code);
    String getType();
}
