package com.codesage.Task.Tracking.services.impl;

import com.codesage.Task.Tracking.domain.entities.TaskList;
import com.codesage.Task.Tracking.repositories.TaskListRepository;
import com.codesage.Task.Tracking.services.TaskListServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServicesImpl implements TaskListServices {

    @Autowired
    public TaskListRepository taskListRepository;
    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if (taskList.getId() != null) {
            throw new IllegalArgumentException("Task list id is already set");
        }
        if (taskList.getTitle()==null) {
            throw new IllegalArgumentException("Task list title is null");
        }
        LocalDateTime now = LocalDateTime.now();
         return taskListRepository.save(new TaskList(
                 null,
                 taskList.getTitle(),
                 taskList.getDescription(),
                 now,
                 now,
                 null
         ));
    }

    @Override
    public Optional<TaskList> getTaskListById(UUID id) {
        return taskListRepository.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if (taskList.getId() == null) {
            throw new IllegalArgumentException("Task list must have an id");
        }
        if (taskList.getTitle()==null) {
            throw new IllegalArgumentException("Task list must have an title");
        }

        //or else will throw if existingTaskList is empty
        TaskList existingTaskList=taskListRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Task list not found"));

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdated(LocalDateTime.now());
        return taskListRepository.save(existingTaskList);
    }


    @Transactional
    @Override
    public void deleteTaskList(UUID id) {
        taskListRepository.deleteById(id);
    }
}
