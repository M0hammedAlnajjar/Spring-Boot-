package com.cl.demo.controllers;
import com.cl.demo.services.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cl.demo.requestobjects.TaskCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
}
