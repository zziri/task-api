package com.zziri.task.controller;

import com.zziri.task.service.GoogleService;
import com.zziri.task.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/social/login")
public class SocialController {

    private final Environment env;
    private final KakaoService kakaoService;
    private final GoogleService googleService;

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
        mav.addObject("authInfo", kakaoService.getTokenInfo(code));
        mav.setViewName("social/redirect");
        return mav;
    }

    @GetMapping(value = "/google")
    public ModelAndView redirectGoogle(ModelAndView mav, @RequestParam String code) {
        mav.addObject("authInfo", googleService.getTokenInfo(code));
        mav.setViewName("social/redirect");
        return mav;
    }
}