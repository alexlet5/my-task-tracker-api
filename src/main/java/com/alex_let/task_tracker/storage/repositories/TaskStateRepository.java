package com.alex_let.task_tracker.storage.repositories;

import com.alex_let.task_tracker.storage.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity,Long>
{

}
