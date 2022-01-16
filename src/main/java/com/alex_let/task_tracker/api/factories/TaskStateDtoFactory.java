package com.alex_let.task_tracker.api.factories;

import com.alex_let.task_tracker.api.dto.TaskStateDto;
import com.alex_let.task_tracker.storage.entities.TaskStateEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class TaskStateDtoFactory //псевдофабрика делающая дто из энтити
{
    TaskDtoFactory taskDtoFactory;
    public TaskStateDto makeTaskStateDto(TaskStateEntity entity)
    {
        return TaskStateDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .ordinal(entity.getOrdinal())
            .createdAt(entity.getCreatedAt())
            .tasks(
                entity
                    .getTasks()
                    .stream()
                    .map(taskDtoFactory::makeTaskDto)
                    .collect(Collectors.toList()))
            .build();
    }

}
