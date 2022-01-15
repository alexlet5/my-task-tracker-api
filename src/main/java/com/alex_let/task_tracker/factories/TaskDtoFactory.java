package com.alex_let.task_tracker.factories;

import com.alex_let.task_tracker.dto.TaskDto;
import com.alex_let.task_tracker.entities.TaskEntity;
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
