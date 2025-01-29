package com.codesage.Task.Tracking.domain.dto;

import com.codesage.Task.Tracking.domain.entities.TaskPriority;
import com.codesage.Task.Tracking.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/*record generate immutable object and automatically generate toString(), equals(),
hashCode(), and constructor based on the record fields.*/

/*in dto we use that only for returning and retreving data from user, that's why
entity use for database , that's why dto and entity data are difference*/

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus Status
) {

}
