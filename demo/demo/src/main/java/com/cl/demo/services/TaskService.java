package com.cl.demo.services;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    public static final String Task_SAVED = "Task_SAVED";
public String  generateTaskNumber(){
    return "TASK-"+ UUID.randomUUID().toString().substring(0, 8).toUpperCase();
}

}
