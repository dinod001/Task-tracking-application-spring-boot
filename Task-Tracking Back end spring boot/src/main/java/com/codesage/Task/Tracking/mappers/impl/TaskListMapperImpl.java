package com.codesage.Task.Tracking.mappers.impl;

import com.codesage.Task.Tracking.domain.dto.TaskListDto;
import com.codesage.Task.Tracking.domain.entities.Task;
import com.codesage.Task.Tracking.domain.entities.TaskList;
import com.codesage.Task.Tracking.domain.entities.TaskStatus;
import com.codesage.Task.Tracking.mappers.TaskListMapper;
import com.codesage.Task.Tracking.mappers.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {
    @Autowired
    public TaskMapper taskMapper;
    
    @Override
    public TaskList fromdto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                null,
                null,
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks->tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList()
                        ).orElse(null)

        );
    }

    @Override
    public TaskListDto todto(TaskList taskList) {
       return new TaskListDto(
               taskList.getId(),
               taskList.getTitle(),
               taskList.getDescription(),
               Optional.ofNullable(
                       taskList.getTasks()).map(List::size)
                       .orElse(null),
               calculateTaskListProgress(taskList.getTasks()),
               Optional.ofNullable(taskList.getTasks()).
                       map(tasks -> tasks.stream().map(taskMapper::toDto).toList()).
                       orElse(null)

        );
    }

    private double calculateTaskListProgress(List<Task> tasks) {
        if (tasks==null) {
            return 0;
        }
        long count = tasks.stream().filter(task ->
                        task.getStatus() == TaskStatus.CLOSED)
                .count();
        return (double) count / tasks.size();
    }
}
