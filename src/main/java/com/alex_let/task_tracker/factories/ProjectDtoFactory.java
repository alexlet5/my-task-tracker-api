package com.alex_let.task_tracker.factories;

import com.alex_let.task_tracker.dto.ProjectDto;
import com.alex_let.task_tracker.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory
{
    public ProjectDto makeProjectDto(ProjectEntity entity)
    {
        return ProjectDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }
}
