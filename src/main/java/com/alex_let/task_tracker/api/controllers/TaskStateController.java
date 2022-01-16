package com.alex_let.task_tracker.api.controllers;

import com.alex_let.task_tracker.api.dto.TaskStateDto;
import com.alex_let.task_tracker.api.factories.TaskStateDtoFactory;
import com.alex_let.task_tracker.storage.entities.ProjectEntity;
import com.alex_let.task_tracker.storage.repositories.TaskStateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class TaskStateController
{

    TaskStateDtoFactory taskStateDtoFactory;

    TaskStateRepository taskStateRepository;

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
