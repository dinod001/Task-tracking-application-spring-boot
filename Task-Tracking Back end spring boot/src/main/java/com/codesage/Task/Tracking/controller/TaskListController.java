package com.codesage.Task.Tracking.controller;

import com.codesage.Task.Tracking.domain.dto.TaskListDto;
import com.codesage.Task.Tracking.domain.entities.TaskList;
import com.codesage.Task.Tracking.mappers.impl.TaskListMapperImpl;
import com.codesage.Task.Tracking.services.impl.TaskListServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/task-lists")
public class TaskListController {

    @Autowired
    public TaskListServicesImpl taskListServices;
    @Autowired
    public TaskListMapperImpl taskListMapper;

    @GetMapping
    public List<TaskListDto> lisTaskList(){
        return taskListServices.listTaskLists()
                .stream()
                .map(taskListMapper::todto).toList();
    }

    @PostMapping
    public TaskListDto createdTaskList(@RequestBody TaskListDto taskListDto){
        TaskList createdTaskList = taskListServices.createTaskList(
                taskListMapper.fromdto(taskListDto)
        );
        return taskListMapper.todto(createdTaskList);
    }

    @GetMapping("/{task_list_id}")
    public Optional<TaskListDto> getTaskListById(@PathVariable("task_list_id")UUID id){
        return taskListServices.getTaskListById(id).map(taskListMapper::todto);
    }

    @PutMapping("/{task_list_id}")
    public TaskListDto updatedTaskList(@PathVariable("task_list_id")UUID id, @RequestBody TaskListDto taskListDto){
        TaskList UpadtedtaskList = taskListServices.updateTaskList(id, taskListMapper.fromdto(taskListDto));
        return taskListMapper.todto(UpadtedtaskList);
    }

    @DeleteMapping("/{task_list_id}")
    public void deleteTaskList(@PathVariable("task_list_id")UUID id){
        taskListServices.deleteTaskList(id);
    }
}
