package com.codesage.Task.Tracking.mappers;

import com.codesage.Task.Tracking.domain.dto.TaskDto;
import com.codesage.Task.Tracking.domain.entities.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);
    TaskDto toDto(Task task);
}
