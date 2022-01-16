package com.alex_let.task_tracker.api.factories;

import com.alex_let.task_tracker.api.dto.ProjectDto;
import com.alex_let.task_tracker.storage.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDtoFactory //псевдофабрика делающая дто из энтити
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
