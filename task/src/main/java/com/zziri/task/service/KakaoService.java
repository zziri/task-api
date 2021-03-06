package com.zziri.task.service;

import com.google.gson.Gson;
import com.zziri.task.domain.KakaoProfile;
import com.zziri.task.domain.OAuthTokenInfo;
import com.zziri.task.domain.SocialProfile;
import com.zziri.task.exception.custom.CommunicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class KakaoService implements SocialService {

    private final RestTemplate restTemplate;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;
    @Value("${spring.social.kakao.client_id}")
    private String clientId;
    @Value("${spring.social.kakao.redirect}")
    private String redirectUrl;
    @Value("${spring.social.kakao.url.profile}")
    private String profileUrl;
    @Value("${spring.social.kakao.url.token}")
    private String tokenUrl;

    @Override
    public SocialProfile getProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(profileUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), KakaoProfile.class);
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }

    @Override
    public OAuthTokenInfo getTokenInfo(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", baseUrl + redirectUrl);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return gson.fromJson(response.getBody(), OAuthTokenInfo.class);
            }
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }

    @Override
    public String getType() {
        return "kakao";
    }
}
