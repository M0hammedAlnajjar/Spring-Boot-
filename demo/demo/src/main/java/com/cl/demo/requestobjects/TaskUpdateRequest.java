package com.cl.demo.requestobjects;

import com.cl.demo.entities.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskUpdateRequest {

    private String uuid;
    private String titleToUpdate;
    private String descriptionToUpdate;
    private TaskStatus taskStatusToUpdate;
    private Date dueDateToUpdate;
    private Boolean isAssignedToUpdate;
}