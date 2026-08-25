package com.cl.demo.responseobjects;

import com.cl.demo.entities.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskCreateResponse {

    private String taskId;
    private String title;
    private String description;
    private String taskNumber;
    private String taskStatus;
    private String dueDate;
    private Boolean isAssigned;

    // Convert one Task
    public static TaskCreateResponse convert(Task task) {

        if (task == null || task.getId() == null) {
            return null;
        }

        TaskCreateResponse response =
                new TaskCreateResponse();

        response.setTaskId(task.getId().toString());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setTaskNumber(task.getTaskNumber());

        if (task.getTaskStatus() != null) {
            response.setTaskStatus(
                    task.getTaskStatus().toString()
            );
        }

        if (task.getDueDate() != null) {
            response.setDueDate(
                    task.getDueDate().toString()
            );
        }

        response.setIsAssigned(task.getIsAssigned());

        return response;
    }

    // Convert a List of Tasks
    public static List<TaskCreateResponse> convert(
            List<Task> tasks
    ) {

        List<TaskCreateResponse> responses =
                new ArrayList<>();

        for (Task task : tasks) {

            TaskCreateResponse response = convert(task);

            if (response != null) {
                responses.add(response);
            }
        }

        return responses;
    }
}