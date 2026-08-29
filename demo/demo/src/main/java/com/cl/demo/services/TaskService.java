package com.cl.demo.services;

import com.cl.demo.DemoApplication;
import com.cl.demo.entities.Task;
import com.cl.demo.requestobjects.TaskCreateRequest;
import com.cl.demo.requestobjects.TaskUpdateRequest;
import com.cl.demo.utils.HelperUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    public Map<String, String> addTask(
            TaskCreateRequest taskCreateRequest
    ) {

        Map<String, String> response =
                new HashMap<>();

        if (taskCreateRequest == null
                || taskCreateRequest.getTitle() == null
                || taskCreateRequest.getTitle().isBlank()) {

            response.put(
                    "error",
                    TASK_TITLE_REQUIRED
            );

            return response;
        }

        Task task = new Task();

        task.setId(UUID.randomUUID());
        task.setIsActive(Boolean.TRUE);
        task.setCreatedDate(new Date());
        task.setTaskNumber(generateTaskNumber());

        task.setTitle(taskCreateRequest.getTitle());
        task.setDescription(
                taskCreateRequest.getDescription()
        );
        task.setDueDate(
                taskCreateRequest.getDueDate()
        );
        task.setStartDate(
                taskCreateRequest.getStartDate()
        );
        task.setTaskStatus(
                taskCreateRequest.getTaskStatus()
        );
        task.setIsAssigned(
                taskCreateRequest.getIsAssigned()
        );

        if (DemoApplication.Task_List.add(task)) {

            response.put(
                    "response",
                    TASK_SAVED
            );
        }

        return response;
    }

    public Task getTaskById(String uuid) {

        if (uuid == null || uuid.isBlank()) {
            return new Task();
        }

        for (Task task : DemoApplication.Task_List) {

            if (task.getId() != null
                    && task.getId()
                    .toString()
                    .equals(uuid)
                    && Boolean.TRUE.equals(
                    task.getIsActive()
            )) {

                return task;
            }
        }

        return new Task();
    }

    public List<Task> getAllTasks() {

        List<Task> activeTasks =
                new ArrayList<>();

        for (Task task : DemoApplication.Task_List) {

            if (Boolean.TRUE.equals(
                    task.getIsActive()
            )) {

                activeTasks.add(task);
            }
        }

        return activeTasks;
    }

    public Task updateTask(
            TaskUpdateRequest taskUpdateRequest
    ) {

        if (taskUpdateRequest == null
                || taskUpdateRequest.getUuid() == null
                || taskUpdateRequest.getUuid().isBlank()) {

            return new Task();
        }

        Task existingTask = getTaskById(
                taskUpdateRequest.getUuid()
        );

        if (existingTask.getId() == null) {
            return existingTask;
        }

        existingTask.setTitle(
                HelperUtils.compare(
                        existingTask.getTitle(),
                        taskUpdateRequest.getTitleToUpdate()
                )
        );

        existingTask.setDescription(
                HelperUtils.compare(
                        existingTask.getDescription(),
                        taskUpdateRequest
                                .getDescriptionToUpdate()
                )
        );

        existingTask.setDueDate(
                HelperUtils.compare(
                        existingTask.getDueDate(),
                        taskUpdateRequest.getDueDateToUpdate()
                )
        );

        existingTask.setTaskStatus(
                HelperUtils.compare(
                        existingTask.getTaskStatus(),
                        taskUpdateRequest
                                .getTaskStatusToUpdate()
                )
        );

        existingTask.setIsAssigned(
                HelperUtils.compare(
                        existingTask.getIsAssigned(),
                        taskUpdateRequest
                                .getIsAssignedToUpdate()
                )
        );

        existingTask.setUpdatedDate(new Date());

        return existingTask;
    }

    public Boolean deleteById(String uuid) {

        Task taskToDelete = getTaskById(uuid);

        if (taskToDelete.getId() == null
                || !Boolean.TRUE.equals(
                taskToDelete.getIsActive()
        )) {

            return false;
        }

        taskToDelete.setIsActive(Boolean.FALSE);
        taskToDelete.setUpdatedDate(new Date());

        return true;
    }
}