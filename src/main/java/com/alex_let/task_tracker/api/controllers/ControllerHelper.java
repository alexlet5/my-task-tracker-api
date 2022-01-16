package com.alex_let.task_tracker.api.controllers;

import com.alex_let.task_tracker.api.exceptions.CustomExceptionHandler;
import com.alex_let.task_tracker.storage.entities.ProjectEntity;
import com.alex_let.task_tracker.api.exceptions.NotFoundException;
import com.alex_let.task_tracker.storage.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Component
@Transactional
@CustomExceptionHandler
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
