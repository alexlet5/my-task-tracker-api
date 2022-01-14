package com.alex_let.task_tracker.repositories;

import com.alex_let.task_tracker.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity,Long>
{

}
