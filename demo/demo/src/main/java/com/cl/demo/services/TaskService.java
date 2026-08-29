package com.cl.demo.services;

import org.springframework.stereotype.Service;
import com.cl.demo.requestobjects.TaskCreateRequest;
import com.cl.demo.entities.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

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
        if (taskCreateRequest == null || taskCreateRequest.getTitle() == null || taskCreateRequest.getTitle().isBlank()) {
            response.put("error", TASK_TITLE_REQUIRED);
            return response;
        }
        Task task = new Task();

        task.setId(UUID.randomUUID());
        task.setIsActive(Boolean.TRUE);
        task.setCreatedDate(new Date());
        task.setTaskNumber(generateTaskNumber());

        task.setTitle(taskCreateRequest.getTitle());
        task.setDescription(taskCreateRequest.getDescription());
        task.setDueDate(taskCreateRequest.getDueDate());
        task.setStartDate(taskCreateRequest.getStartDate());
        task.setTaskStatus(taskCreateRequest.getTaskStatus());
        task.setIsAssigned(taskCreateRequest.getIsAssigned());

        return response;


    }

}

