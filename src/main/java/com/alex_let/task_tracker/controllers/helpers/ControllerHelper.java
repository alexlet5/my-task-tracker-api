package com.alex_let.task_tracker.controllers.helpers;

import com.alex_let.task_tracker.entities.ProjectEntity;
import com.alex_let.task_tracker.exceptions.NotFoundException;
import com.alex_let.task_tracker.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Component
@Transactional
public class ControllerHelper
{
    ProjectRepository projectRepository;

    public ProjectEntity getProjectOrThrowException(Long projectId)
    {
        return projectRepository
            .findById(projectId)
            .orElseThrow(()->
                new NotFoundException(
                    String.format
                        ("Project with \"%s\" id doesn't exists.", projectId)
                )
            );
    }

}
