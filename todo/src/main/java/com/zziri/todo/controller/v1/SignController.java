package com.zziri.todo.controller.v1;

import com.zziri.todo.domain.KakaoProfile;
import com.zziri.todo.domain.Response;
import com.zziri.todo.service.KakaoService;
import com.zziri.todo.service.security.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class SignController {
    private final SignService signService;
    private final KakaoService kakaoService;

    @Autowired
    public SignController(SignService signService, KakaoService kakaoService) {
        this.signService = signService;
        this.kakaoService = kakaoService;
    }

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
        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        return signService.signinByProvider(provider, profile);
    }

    @PostMapping(value = "/signup/{provider}")
    public Response<String> signupProvider(@PathVariable String provider, @RequestParam String accessToken, @RequestParam String name) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        return signService.signupProvider(provider, name, profile);
    }
}
