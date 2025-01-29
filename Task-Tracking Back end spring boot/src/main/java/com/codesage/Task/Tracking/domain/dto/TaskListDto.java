package com.codesage.Task.Tracking.domain.dto;

/*record generate immutable object and automatically generate toString(), equals(),
hashCode(), and constructor based on the record fields.*/

import java.util.List;
import java.util.UUID;

public record TaskListDto(
        UUID id,
        String title,
        String description,
        Integer count,
        Double progress,
        List<TaskDto> tasks

) {
}
