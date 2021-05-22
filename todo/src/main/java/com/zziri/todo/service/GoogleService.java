package com.zziri.todo.service;

import com.google.gson.Gson;
import com.zziri.todo.domain.GoogleProfile;
import com.zziri.todo.domain.OAuthTokenInfo;
import com.zziri.todo.exception.custom.CommunicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class GoogleService {
    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    public GoogleProfile getProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    env.getProperty("spring.social.google.url.profile"), HttpMethod.GET, new HttpEntity<>(headers),
                    String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String json = response.getBody()
                        .replace("\"given_name\"", "\"givenName\"")
                        .replace("\"family_name\"", "\"familyName\"")
                        .replace("\"email_verified\"", "\"emailVerified\"")
                        .replace("\"sub\"", "\"id\"");
                return gson.fromJson(json, GoogleProfile.class);
            }
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }

    public OAuthTokenInfo getGoogleTokenInfo(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", env.getProperty("spring.social.google.client_id"));
        params.add("client_secret", env.getProperty("spring.social.google.client_secret"));
        params.add("redirect_uri", env.getProperty("spring.url.base") + env.getProperty("spring.social.google.redirect"));
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(Objects.requireNonNull(env.getProperty("spring.social.google.url.token")), request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), OAuthTokenInfo.class);
        }
        return null;
    }
}
