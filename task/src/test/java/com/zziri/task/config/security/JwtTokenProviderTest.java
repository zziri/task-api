package com.zziri.task.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTokenProviderTest {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Test
    void dateObjectDefaultTest() {
        Date date = new Date(0);
        System.out.println(date.toString());
    }

    @Test
    void claimsTest() {
        String token = jwtTokenProvider.createToken("1", Collections.singletonList("ROLE_USER"));
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        List<String> roles = (List<String>) claimsJws.getBody().get("roles");
        System.out.println(roles.toString());
        assertThat(roles.size()).isEqualTo(1);
        assertThat(roles.get(0)).isEqualTo("ROLE_USER");
    }

    @Test
    void getExpiration() throws InterruptedException {
        String token = jwtTokenProvider.createToken("1", Collections.singletonList("ROLE_USER"));
        Thread.sleep(1);
        Date expirationDate = jwtTokenProvider.getExpiration(token).orElse(new Date(0));
        assertThat(expirationDate).isNotNull();
        System.out.println(expirationDate);
    }
}
