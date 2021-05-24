package com.zziri.todo.controller.v1;

import com.google.gson.Gson;
import com.zziri.todo.config.security.JwtTokenProvider;
import com.zziri.todo.service.TodoTaskService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SignService signService;
    @Autowired
    private TodoTaskService todoTaskService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private Gson gson;
    private String token;
    private Long userPk;
    private String pk;

    @BeforeEach
    public void before() {
        if (userPk != null)
            return;
        // TODO: 데이터 추가 작업
        // TODO: static에서 get bean -> https://m.blog.naver.com/PostView.naver?blogId=lminggvick&logNo=221074827418&proxyReferer=https:%2F%2Fwww.google.com%2F
        signService.signup("thisismyaccount", "1234", "jihoon");
        token = signService.signin("thisismyaccount", "1234").getData();
        userPk = Long.valueOf(jwtTokenProvider.getUserPk(token));
        pk = todoTaskService.addTodoTask("first todo task", userPk).getData().getPk();
    }

    @Test
    public void addTodoTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/todo/task")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "    \"title\": \"first todo task\"\n" +
                                "}")
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.error").value("false"))
                .andExpect(jsonPath("$.data.title").value("first todo task"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.body").exists())
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.modifiedAt").exists());
    }

    @Test
    public void getTodoTaskList() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/todo/task")
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("false"))
                .andExpect(jsonPath("$.data.[0].title").exists())
                .andExpect(jsonPath("$.data.[0].id").exists())
                .andExpect(jsonPath("$.data.[0].createdAt").exists())
                .andExpect(jsonPath("$.data.[0].modifiedAt").exists());
    }

    @Test
    public void deleteTodoTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/todo/task/" + pk)
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("false"));
    }

    @Test
    public void modifyTodoTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/todo/task/" + pk)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "    \"title\": \"modified\"\n" +
                                "}")
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("false"))
                .andExpect(jsonPath("$.data.title").value("modified"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.body").exists())
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.modifiedAt").exists());
    }
}
