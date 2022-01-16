package com.alex_let.task_tracker.storage.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "project")
public class ProjectEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(unique = true)
    String name;

    //по дефолту все поля @column
    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    Instant updatedAt = Instant.now();

    @Builder.Default //в билде если не указано иначе то дефалт будет то чему равно снизу
    @OneToMany
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    //все task states получат колонку project_id, возьмут значение из id отсюда^^^
    //это связь многие ко многим, но без доп таблицы
    List<TaskStateEntity> taskStates = new ArrayList<>();
}
