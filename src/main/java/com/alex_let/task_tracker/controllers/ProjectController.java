package com.alex_let.task_tracker.controllers;

import com.alex_let.task_tracker.dto.AckDto;
import com.alex_let.task_tracker.entities.ProjectEntity;
import com.alex_let.task_tracker.exceptions.BadRequestException;
import com.alex_let.task_tracker.dto.ProjectDto;
import com.alex_let.task_tracker.exceptions.NotFoundException;
import com.alex_let.task_tracker.factories.ProjectDtoFactory;
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
public class ProjectController //создавать проекты, редактировать, удалять
{
    ProjectDtoFactory projectDtoFactory;

    ProjectRepository projectRepository;

    public static final String CREATE_PROJECT = "/api/projects";
    public static final String FETCH_PROJECTS = "/api/projects";
    public static final String EDIT_PROJECT = "/api/projects/{project_id}";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";
    public static final String CREATE_OR_UPDATE_PROJECT = "/api/projects";

    @GetMapping(FETCH_PROJECTS)
    public List<ProjectDto> fetchProjects(
        @RequestParam(value = "prefix_name", required = false)Optional<String> optionalPrefixName)
    {
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
            .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
            .orElseGet(projectRepository::streamAllBy);

        return projectStream.map(projectDtoFactory::makeProjectDto)
            .collect(Collectors.toList());
    }

    //если принимаем параметры, а не dto в body, мы можем указывать
    //какие значения являются обязательными, а какие нет
    //принимать dto опасно из-за инъекций
    //если принимать dto, то делаем приватный класс consumer внутри контроллера?
    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name)
    {
        if(name.trim().isEmpty())
        { throw new BadRequestException("Name can't be empty");}

        projectRepository.findByName(name).ifPresent(projectEntity ->
        {throw new BadRequestException(String.format("Project \"%s\" already exists!", name));}  );

        //обычный save не генерирует idшник, транзакция не пройдет при неудачном методе
        ProjectEntity project = projectRepository.saveAndFlush(
            ProjectEntity.builder()
                .name(name)
                .build());

        return projectDtoFactory.makeProjectDto(project);
    }

    @PutMapping(CREATE_OR_UPDATE_PROJECT)
    public ProjectDto createOrUpdateProject(
        @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
        @RequestParam(value = "project_name", required = false ) Optional<String> optionalProjectName)
    {

        optionalProjectName = optionalProjectName.filter(projectName/*не особо понятно*/-> !projectName.trim().isEmpty());

         //если не передали id, то создаем проект, если имя тоже не передали, то ошибка
        if(optionalProjectId.isEmpty() && optionalProjectName.isEmpty())
        {
            throw new BadRequestException("Project name can't be empty.");
        }

        ProjectEntity project = optionalProjectId //получаем проджект или делаем новый
            .map(this::getProjectOrThrowException)
            .orElseGet(()-> ProjectEntity.builder().build());

        optionalProjectName
            .ifPresent(projectName-> projectRepository
                .findByName(projectName)
                .filter(anotherProject ->
                    !Objects.equals(anotherProject.getId(),project.getId()))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(
                        String.format("Project \"%s\" already exists.", projectName)
                    );
                }));

        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeProjectDto(savedProject);
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editProject(@PathVariable("project_id") Long projectId,
                                    @RequestParam String projectName)
    {
        if(projectName.trim().isEmpty())
        { throw new BadRequestException("Name can't be empty");}

        ProjectEntity project = getProjectOrThrowException(projectId);

        projectRepository.findByName(projectName)
            .filter(anotherProject ->
                !Objects.equals(anotherProject.getId(),projectId))
            .ifPresent(anotherProject -> {
                throw new BadRequestException(String.format("Project \"%s\" already exists.", projectName)
                );
            });

        project.setName(projectName);

        project = projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeProjectDto(project);
    }

    @DeleteMapping(DELETE_PROJECT)
    public AckDto deleteProject(@PathVariable("project_id")Long projectId)
    {
        getProjectOrThrowException(projectId);

        projectRepository.deleteById(projectId);

        return AckDto.makeDefault(true);
    }

    private ProjectEntity getProjectOrThrowException(Long projectId)
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