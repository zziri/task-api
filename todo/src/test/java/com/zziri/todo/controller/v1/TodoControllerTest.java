package com.zziri.todo.controller.v1;

import com.zziri.todo.service.security.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SignService signService;

    private String token;

    @BeforeEach
    public void before() {
        // ToDo: BeforeAll로 바꾸기, static에서 get bean -> https://m.blog.naver.com/PostView.naver?blogId=lminggvick&logNo=221074827418&proxyReferer=https:%2F%2Fwww.google.com%2F
        signService.signup("thisismyaccount", "1234", "jihoon");
        token = signService.signin("thisismyaccount", "1234").getData();
    }

    @Test
    public void addTodoTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/todo")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("task", "show me what to do")
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}