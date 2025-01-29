package com.codesage.Task.Tracking.mappers;

import com.codesage.Task.Tracking.domain.dto.TaskListDto;
import com.codesage.Task.Tracking.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromdto(TaskListDto taskListDto);
    TaskListDto todto(TaskList taskList);
}
