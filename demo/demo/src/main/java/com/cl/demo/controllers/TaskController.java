package com.cl.demo.controllers;
import com.cl.demo.services.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cl.demo.requestobjects.TaskCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.cl.demo.responseobjects.TaskCreateResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.cl.demo.requestobjects.TaskUpdateRequest;
import com.cl.demo.responseobjects.TaskUpdateResponse;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;
@RestController
@RequestMapping("task")


public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public Map<String, String> addTask(
            @RequestBody TaskCreateRequest taskCreateRequest
    ) {

        return taskService.addTask(taskCreateRequest);
    }
    @GetMapping("/getById")
    public TaskCreateResponse getTaskById(
            @RequestParam String uuid
    ) {

        return taskService.getTaskById(uuid);
    }
    @GetMapping("/getAll")
    public List<TaskCreateResponse> getAllTasks() {

        return taskService.getAllTasks();
    }
    @PutMapping("/update")
    public TaskUpdateResponse updateTask(
            @RequestBody TaskUpdateRequest taskUpdateRequest
    ) {

        return taskService.updateTask(taskUpdateRequest);
    }
}
