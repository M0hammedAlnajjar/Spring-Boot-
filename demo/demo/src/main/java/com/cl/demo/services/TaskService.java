package com.cl.demo.services;

import org.springframework.stereotype.Service;

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
}