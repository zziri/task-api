package com.zziri.todo.controller.v1;

import com.zziri.todo.domain.GoogleProfile;
import com.zziri.todo.domain.KakaoProfile;
import com.zziri.todo.domain.Response;
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
        if (provider.equals("kakao")) {
            KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
            return signService.signinByProvider(provider, profile);
        } else if (provider.equals("google")) {
            GoogleProfile profile = googleService.getProfile(accessToken);
            return signService.signinByProvider(provider, profile);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/signup/{provider}")
    public Response<String> signupProvider(@PathVariable String provider, @RequestParam String accessToken, @RequestParam String name) {
        if (provider.equals("kakao")) {
            KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
            return signService.signupProvider(provider, name, profile);
        } else if (provider.equals("google")) {
            GoogleProfile profile = googleService.getProfile(accessToken);
            return signService.signupProvider(provider, name, profile);
        } else {
            return null;
        }
    }
}
