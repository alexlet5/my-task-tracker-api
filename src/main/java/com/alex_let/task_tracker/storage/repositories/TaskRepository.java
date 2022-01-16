package com.alex_let.task_tracker.storage.repositories;

import com.alex_let.task_tracker.storage.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity,Long>
{


}
