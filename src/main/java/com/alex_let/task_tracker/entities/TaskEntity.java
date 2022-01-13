package com.alex_let.task_tracker.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "task")
public class TaskEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(unique = true)
    String name;

    Long ordinal; //номер для сортировки

    //по дефолту все поля @column
    Instant createdAt = Instant.now();

    String description;

    @OneToMany
    List<TaskEntity> tasks;
}
