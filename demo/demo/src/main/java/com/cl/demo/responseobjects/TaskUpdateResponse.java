package com.cl.demo.responseobjects;
import com.cl.demo.entities.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaskUpdateResponse {
    private String taskId;
    private String title;
    private String description;
    private String taskNumber;
    private String taskStatus;
    private String dueDate;
    private Boolean isAssigned;

    public static TaskUpdateResponse convert(Task task) {
        if (task == null || task.getId() == null){
        return null;
    }
        TaskUpdateResponse taskUpdateResponse = new TaskUpdateResponse();
        taskUpdateResponse.setTaskId(task.getId().toString());
        taskUpdateResponse.setTitle(task.getTitle());
        taskUpdateResponse.setDescription(task.getDescription());
        taskUpdateResponse.setTaskNumber(task.getTaskNumber());

        if (task.getTaskStatus() != null){
            taskUpdateResponse.setTaskStatus(task.getTaskStatus().toString());

        }
        if (task.getDueDate() != null){
            taskUpdateResponse.setDueDate(task.getDueDate().toString());
        }
        return taskUpdateResponse;

}
    public static List<TaskUpdateResponse> convert(
            List<Task> taskList
    ) {

        List<TaskUpdateResponse> responseList =
                new ArrayList<>();

        for (Task task : taskList) {

            TaskUpdateResponse response = convert(task);

            if (response != null) {
                responseList.add(response);
            }
        }

        return responseList;
    }

}
