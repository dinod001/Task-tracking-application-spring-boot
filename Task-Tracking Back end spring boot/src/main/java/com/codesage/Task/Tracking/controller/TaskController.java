package com.codesage.Task.Tracking.controller;


import com.codesage.Task.Tracking.domain.dto.TaskDto;
import com.codesage.Task.Tracking.domain.entities.Task;
import com.codesage.Task.Tracking.mappers.TaskMapper;
import com.codesage.Task.Tracking.services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists/{task_list_id}/tasks")
public class TaskController {

    @Autowired
    public TaskServices taskServices;
    @Autowired
    public TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task_list_id")UUID id) {
        return taskServices.listTasks(id)
                .stream()
                .map(taskMapper::toDto).toList();
    }

    @PostMapping
    public TaskDto createTask(@PathVariable("task_list_id")UUID id, @RequestBody TaskDto taskDto) {
        Task CreatedTask = taskServices.createTask(id, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(CreatedTask);
    }

    @GetMapping("/{task_id}")
    public Optional<TaskDto> getTask(@PathVariable("task_id")UUID id,
                                     @PathVariable("task_list_id")UUID taskListId) {

        return taskServices.getTask(taskListId,id).map(taskMapper::toDto);
    }

    @PutMapping("/{task_id}")
    public TaskDto updateTask(@PathVariable("task_id")UUID id,
                              @PathVariable("task_list_id")UUID taskListId,
                              @RequestBody TaskDto taskDto) {
        Task updatedTask = taskServices.updateTask(taskListId, id, taskMapper.fromDto(taskDto));
        return taskMapper.toDto(updatedTask);
    }


    @DeleteMapping("/{task_id}")
    public void deletTask(@PathVariable("task_id")UUID id,
                          @PathVariable("task_list_id")UUID taskListId){
        taskServices.deleteTask(taskListId, id);
    }

}
