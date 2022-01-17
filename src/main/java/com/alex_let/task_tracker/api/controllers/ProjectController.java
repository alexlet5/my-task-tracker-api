package com.alex_let.task_tracker.api.controllers;

import com.alex_let.task_tracker.api.dto.ProjectDto;
import com.alex_let.task_tracker.api.exceptions.CustomExceptionHandler;
import com.alex_let.task_tracker.api.factories.ProjectDtoFactory;
import com.alex_let.task_tracker.api.dto.AckDto;
import com.alex_let.task_tracker.storage.entities.ProjectEntity;
import com.alex_let.task_tracker.api.exceptions.BadRequestException;
import com.alex_let.task_tracker.storage.repositories.ProjectRepository;
import com.alex_let.task_tracker.storage.repositories.TaskRepository;
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
@CustomExceptionHandler //подключает CustomExceptionAdvice
public class ProjectController
{

    ProjectRepository projectRepository;

    ProjectDtoFactory projectDtoFactory;

    ControllerHelper controllerHelper;

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
    public ProjectDto createProject(@RequestParam String project_name)
    {
        if(project_name.trim().isEmpty())
        { throw new BadRequestException("Name can't be empty");}

        projectRepository.findByName(project_name).ifPresent(projectEntity ->
        {throw new BadRequestException(String.format("Project \"%s\" already exists!", project_name));}  );

        //обычный save не генерирует idшник в ProjectEntity, дто построится без id -> фигня
        ProjectEntity project = projectRepository.saveAndFlush(
            ProjectEntity.builder()
                .name(project_name)
                .build());

        return projectDtoFactory.makeProjectDto(project);
    }

    @PutMapping(CREATE_OR_UPDATE_PROJECT)
    public ProjectDto createOrUpdateProject(
        @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
        @RequestParam(value = "project_name", required = false ) Optional<String> optionalProjectName)
    {
        //обрезаем лишние символы
        optionalProjectName = optionalProjectName.filter(projectName-> !projectName.trim().isEmpty());

         //если не передали id, то создаем проект, если имя тоже не передали, то ошибка
        if(optionalProjectId.isEmpty() && optionalProjectName.isEmpty())
        {
            throw new BadRequestException("Project name can't be empty.");
        }

        ProjectEntity project = optionalProjectId //получаем проджект или делаем пустой новый
            .map(controllerHelper::getProjectOrThrowException)
            .orElseGet(()-> ProjectEntity.builder().build());


        optionalProjectName
            //если есть имя то
            .ifPresent(projectName-> {
                projectRepository
                    //ищем по имени объект
                .findByName(projectName)
                .filter(anotherProject ->
                    //если найденный объект имеет другой id то бросаем исключение, совпадения имен у нас недопустимы
                    //иначе если объект с именем запроса имеет наш id то переименования не было, ошибки нет
                    !Objects.equals(anotherProject.getId(),project.getId()))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(
                        String.format("Project \"%s\" already exists.", projectName));
                });
                //если все ок и нет эксепшена то переименовываем
                /*if(project.getName() != projectName)
                    project.setUpdatedAt(Instant.now());*/
                project.setName(projectName);
            });

        //заменяем объект
        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        //если не было ошибок и проект сохранен то возвращаем дто из энтити
        return projectDtoFactory.makeProjectDto(savedProject);
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editProject(@PathVariable("project_id") Long projectId,
                                    @RequestParam(value = "project_name") String projectName)
    {
        if(projectName.trim().isEmpty())
        { throw new BadRequestException("Name can't be empty");}

        ProjectEntity project = controllerHelper.getProjectOrThrowException(projectId);

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
        controllerHelper.getProjectOrThrowException(projectId);

        projectRepository.deleteById(projectId);

        return AckDto.makeDefault(true);
    }



}
