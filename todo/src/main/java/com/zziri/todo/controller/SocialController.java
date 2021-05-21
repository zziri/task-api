package com.zziri.todo.controller;

import com.google.gson.Gson;
import com.zziri.todo.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/social/login")
public class SocialController {

    private final Environment env;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final KakaoService kakaoService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;

    @GetMapping
    public ModelAndView socialLogin(ModelAndView mav) {
        StringBuilder kakaoLoginUrl = new StringBuilder()
                .append(env.getProperty("spring.social.kakao.url.login"))
                .append("?client_id=").append(kakaoClientId)
                .append("&response_type=code")
                .append("&redirect_uri=").append(baseUrl).append(kakaoRedirect);
        mav.addObject("kakaoLoginUrl", kakaoLoginUrl);

        StringBuilder googleLoginUrl = new StringBuilder()
                .append(env.getProperty("spring.social.google.url.login"))
                .append("?scope=").append(env.getProperty("spring.social.google.scope"))
                .append("&client_id=").append(env.getProperty("spring.social.google.client_id"))
                .append("&redirect_uri=").append(baseUrl).append(env.getProperty("spring.social.google.redirect"))
                .append("&response_type=code");

        mav.addObject("googleLoginUrl", googleLoginUrl);

        mav.setViewName("social/login");
        return mav;
    }

    @GetMapping(value = "/kakao")
    public ModelAndView redirectKakao(ModelAndView mav, @RequestParam String code) {
        mav.addObject("authInfo", kakaoService.getKakaoTokenInfo(code));
        mav.setViewName("social/redirectKakao");
        return mav;
    }

    @GetMapping(value = "/google")
    public String redirectGoogle(@RequestParam String code) {
        return code;
    }
}