package com.codesage.Task.Tracking.services;

import com.codesage.Task.Tracking.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskServices {
    List<Task> listTasks(UUID TaskListId);
    Task createTask(UUID TaskListId, Task task);
    Optional<Task> getTask(UUID TaskId,UUID TaskListId);
    Task updateTask(UUID TaskListId, UUID TaskId, Task task);
    void deleteTask(UUID TaskListId, UUID TaskId);
}
