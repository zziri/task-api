package com.zziri.todo.service;

import com.zziri.todo.domain.Response;
import com.zziri.todo.domain.TodoTask;
import com.zziri.todo.exception.custom.TaskNotFoundException;
import com.zziri.todo.repository.TodoTaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class TodoTaskService {
    private final TodoTaskRepo todoTaskRepo;

    public Response<TodoTask> addTodoTask(TodoTask todoTask, Long userPk) {
        todoTask.setOwnerId(userPk);
        todoTask = todoTaskRepo.save(todoTask);
        return Response.<TodoTask>builder()
                .data(todoTask).build();
    }

    public Response<List<TodoTask>> getTodoTasks(Long userPk) {
        List<TodoTask> todoTasks = todoTaskRepo.findByOwnerId(userPk);
        return Response.<List<TodoTask>>builder()
                .data(todoTasks).build();
    }

    public Response<TodoTask> patchTodoTask(TodoTask input, Long taskId, Long userPk) {
        TodoTask origin = todoTaskRepo.findByOwnerIdAndId(userPk, taskId).orElseThrow(TaskNotFoundException::new);
        origin.patch(input);
        return Response.<TodoTask>builder()
                .data(todoTaskRepo.save(origin)).build();
    }

    public Response<Object> deleteTodoTask(Long taskId, Long userPk) {
        TodoTask toBeDeleted = todoTaskRepo.findByOwnerIdAndId(userPk, taskId).orElseThrow(TaskNotFoundException::new);
        todoTaskRepo.delete(toBeDeleted);
        return Response.builder().build();
    }

    public Response<List<TodoTask>> getTodoTasksDiff(Long userPk, List<TodoTask> tasks) {
        Map<Long, TodoTask> tasksAtServer = mapping(todoTaskRepo.findByOwnerId(userPk));
        Map<Long, TodoTask> tasksAtClient = mapping(tasks);
        List<TodoTask> result = getDiff(tasksAtServer, tasksAtClient);
        return Response.<List<TodoTask>>builder()
                .data(result).build();
    }

    private List<TodoTask> getDiff(Map<Long, TodoTask> server, Map<Long, TodoTask> client) {
        List<TodoTask> ret = new ArrayList<>();

        for (Map.Entry<Long, TodoTask> entry : server.entrySet()) {
            Long id = entry.getKey();
            TodoTask task = entry.getValue();

            if (!client.containsKey(id)) {
                ret.add(task);
            } else {
                if (!task.contentEquals(client.get(id)))
                    ret.add(task);
            }
        }

        return ret;
    }

    private Map<Long, TodoTask> mapping(List<TodoTask> tasks) {
        Map<Long, TodoTask> ret = new HashMap<>();
        for (TodoTask task : tasks) {
            ret.put(task.getId(), task);
        }
        return ret;
    }
}
