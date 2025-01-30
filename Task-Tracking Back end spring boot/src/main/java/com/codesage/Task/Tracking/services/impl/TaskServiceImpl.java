package com.codesage.Task.Tracking.services.impl;

import com.codesage.Task.Tracking.domain.entities.Task;
import com.codesage.Task.Tracking.domain.entities.TaskList;
import com.codesage.Task.Tracking.domain.entities.TaskPriority;
import com.codesage.Task.Tracking.domain.entities.TaskStatus;
import com.codesage.Task.Tracking.repositories.TaskListRepository;
import com.codesage.Task.Tracking.repositories.TaskRepository;
import com.codesage.Task.Tracking.services.TaskServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskServices {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Override
    public List<Task> listTasks(UUID TaskListId) {
        return taskRepository.findByTaskListId(TaskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (task.getId() != null) {
            throw new IllegalArgumentException("Task already has an ID");
        }
        if (task.getTitle()==null || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus status=TaskStatus.OPEN;
        TaskList exisitingTaskList = taskListRepository.findById(taskListId).orElseThrow(() -> new IllegalArgumentException("Task list does not exist"));
        LocalDateTime now=LocalDateTime.now();

        return taskRepository.save(new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                status,
                taskPriority,
                now,
                now,
                exisitingTaskList
        ));
    }

    @Override
    public Optional<Task> getTask(UUID TaskListId, UUID TaskId) {
        return taskRepository.findByTaskListIdAndId(TaskListId, TaskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID TaskListId, UUID TaskId, Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task must have an ID");
        }
        if (!Objects.equals(TaskId, task.getId())) {
            throw new IllegalArgumentException("Task id does not match");
        }
        if (task.getPriority()==null){
            throw new IllegalArgumentException("Task priority must have an priority");
        }
        if (task.getStatus() == null){
            task.setStatus(TaskStatus.OPEN);
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(TaskListId, TaskId)
                .orElseThrow(() -> new IllegalArgumentException("Task does not exist"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID TaskListId, UUID TaskId) {
        taskRepository.deleteByTaskListIdAndId(TaskListId, TaskId);
    }
}
