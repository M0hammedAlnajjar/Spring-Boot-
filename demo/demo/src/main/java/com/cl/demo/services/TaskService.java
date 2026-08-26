package com.cl.demo.services;

import org.springframework.stereotype.Service;
import com.cl.demo.requestobjects.TaskCreateRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TaskService {

    public static final String TASK_SAVED =
            "Task saved";

    public static final String TASK_TITLE_REQUIRED =
            "Task title is required";

    public String generateTaskNumber() {

        return "TASK-"
                + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    public Map<String, String> addTask(TaskCreateRequest taskCreateRequest) {
        Map<String, String> response = new HashMap<>();
    return response ;
    }
}