package com.zziri.todo.controller.v1;

import com.google.gson.Gson;
import com.zziri.todo.config.security.JwtTokenProvider;
import com.zziri.todo.domain.TodoTask;
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

import java.time.LocalDateTime;

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
    private Long taskId;

    @BeforeEach
    public void before() {
        TodoTask todoTask = TodoTask.builder().title("dummy").build();
        signService.signup("thisismyaccount", "1234", "jihoon");
        token = signService.signin("thisismyaccount", "1234").getData();
        Long userPk = Long.valueOf(jwtTokenProvider.getUserPk(token));
        taskId = todoTaskService.addTodoTask(todoTask, userPk).getData().getId();
    }

    @Test
    public void addTodoTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/v2/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "    \"title\": \"first todo task\"\n" +
                                "}")
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.error").value("false"))
                .andExpect(jsonPath("$.data.title").value("first todo task"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.memo").isEmpty())
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.modifiedAt").exists())
                .andExpect(jsonPath("$.data.ownerId").doesNotExist());
    }

    @Test
    public void getTodoTaskList() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/v2/tasks")
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("false"))
                .andExpect(jsonPath("$.data.[0].title").exists())
                .andExpect(jsonPath("$.data.[0].id").exists())
                .andExpect(jsonPath("$.data.[0].createdAt").exists())
                .andExpect(jsonPath("$.data.[0].modifiedAt").exists())
                .andExpect(jsonPath("$.data.[0].memo").isEmpty());
    }

    @Test
    public void deleteTodoTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/v2/tasks/" + taskId)
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("false"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void modifyTodoTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/v2/tasks/" + taskId)
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
                .andExpect(jsonPath("$.data.memo").isEmpty())
                .andExpect(jsonPath("$.data.createdAt").exists())
                .andExpect(jsonPath("$.data.modifiedAt").exists())
                .andExpect(jsonPath("$.data.ownerId").doesNotExist());
    }

    @Test
    public void getTodoTasksDiff() throws Exception {
        TodoTask task = TodoTask.builder()
                .id(1L)
                .title("test title")
                .createdAt(LocalDateTime.parse("2021-06-07T22:48:46.192128"))
                .modifiedAt(LocalDateTime.parse("2021-06-07T22:48:46.192128")).build();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/v2/tasks/diff")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(String.format("[%s]", gson.toJson(task)))
                        .header("X-AUTH-TOKEN", token))
                .andDo(print())
                .andExpect(jsonPath("$.data.[0].title").value("dummy"));
    }
}
