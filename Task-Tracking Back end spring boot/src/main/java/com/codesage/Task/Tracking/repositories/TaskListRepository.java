package com.codesage.Task.Tracking.repositories;

import com.codesage.Task.Tracking.domain.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskListRepository extends JpaRepository<TaskList, UUID> {


}
