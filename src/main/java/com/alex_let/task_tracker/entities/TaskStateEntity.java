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
@Table(name = "task_state")
public class TaskStateEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String name;

    private Long ordinal; //номер для сортировки

    //по дефолту все поля @column
    private Instant createdAt = Instant.now();

    private String description;

    @OneToMany
    private List<TaskEntity> tasks;

}
