package com.alex_let.task_tracker.api.factories;

import com.alex_let.task_tracker.api.dto.TaskDto;
import com.alex_let.task_tracker.storage.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory //псевдофабрика делающая дто из энтити
{
    public TaskDto makeTaskDto(TaskEntity entity)
    {
        return TaskDto
            .builder()
            .id(entity.getId())
            .name(entity.getName())
            .createdAt(entity.getCreatedAt())
            .build();
    }
}
