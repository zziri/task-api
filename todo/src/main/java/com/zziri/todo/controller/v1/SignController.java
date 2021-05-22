package com.zziri.todo.controller.v1;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.SocialProfile;
import com.zziri.todo.service.GoogleService;
import com.zziri.todo.service.KakaoService;
import com.zziri.todo.service.security.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;
    private final KakaoService kakaoService;
    private final GoogleService googleService;

    @PostMapping(value = "/signin")
    public Response<String> signin(@RequestParam String account, @RequestParam String password) {
        return signService.signin(account, password);
    }

    @PostMapping("/signup")
    public Response<String> signup(@RequestParam String account, @RequestParam String password, @RequestParam String name) {
        return signService.signup(account, password, name);
    }

    @PostMapping(value = "/signin/{provider}")
    public Response<String> signinByProvider(@PathVariable String provider, @RequestParam String accessToken) {
        SocialProfile profile;
        if (provider.equals("kakao")) {
            profile = kakaoService.getKakaoProfile(accessToken);
        } else if (provider.equals("google")) {
            profile = googleService.getProfile(accessToken);
        } else {
            return null;
        }
        return signService.signinByProvider(provider, profile);
    }

    @PostMapping(value = "/signup/{provider}")
    public Response<String> signupProvider(@PathVariable String provider, @RequestParam String accessToken, @RequestParam String name) {
        SocialProfile profile;
        if (provider.equals("kakao")) {
            profile = kakaoService.getKakaoProfile(accessToken);
        } else if (provider.equals("google")) {
            profile = googleService.getProfile(accessToken);
        } else {
            return null;
        }
        return signService.signupProvider(provider, name, profile);
    }
}
