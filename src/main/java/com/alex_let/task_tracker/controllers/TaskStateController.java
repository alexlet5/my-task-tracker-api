package com.alex_let.task_tracker.controllers;

import com.alex_let.task_tracker.controllers.helpers.ControllerHelper;
import com.alex_let.task_tracker.dto.AckDto;
import com.alex_let.task_tracker.dto.TaskStateDto;
import com.alex_let.task_tracker.entities.ProjectEntity;
import com.alex_let.task_tracker.exceptions.BadRequestException;
import com.alex_let.task_tracker.dto.ProjectDto;
import com.alex_let.task_tracker.exceptions.NotFoundException;
import com.alex_let.task_tracker.factories.ProjectDtoFactory;
import com.alex_let.task_tracker.factories.TaskStateDtoFactory;
import com.alex_let.task_tracker.repositories.ProjectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskStateController
{

    TaskStateDtoFactory taskStateDtoFactory;

    ControllerHelper controllerHelper;

    public static final String GET_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String CREATE_TASK_STATE = "/api/projects/{project_id}/task-states";
    public static final String UPDATE_TASK_STATE = "/api/task-states/{task_state_id}";
    public static final String CHANGE_TASK_STATE_POSITION = "/api/task-states/{task_state_id}/position/change";
    public static final String DELETE_TASK_STATE = "/api/task-states/{task_state_id}";


    @GetMapping(GET_TASK_STATES)
        public List<TaskStateDto> getTaskStates(@PathVariable(name="project_id")Long projectId)
    {

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

        return project
            .getTaskStates()
            .stream()
            .map(taskStateDtoFactory::makeTaskStateDto)
            .collect(Collectors.toList());

    }




}
