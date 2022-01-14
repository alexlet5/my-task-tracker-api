package com.alex_let.task_tracker.factories;

import com.alex_let.task_tracker.dto.TaskStateDto;
import com.alex_let.task_tracker.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDtoFactory
{

    public TaskStateDto makeTaskStateDto(TaskStateEntity entity)
    {
        return TaskStateDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .ordinal(entity.getOrdinal())
            .createdAt(entity.getCreatedAt())
            .build();
    }

}
