package com.alex_let.task_tracker.entities;

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
@Table(name = "task_state")
public class TaskStateEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    Long ordinal; //номер для сортировки

    //по дефолту все поля @column
    @Builder.Default
    Instant createdAt = Instant.now();

    @OneToMany
    @Builder.Default
    @JoinColumn(name = "task_state_id", referencedColumnName = "id")
    //все tasks получат колонку task_state_id, возьмут значение из id отсюда^^^
    //это связь многие ко многим, но без доп таблицы
    List<TaskEntity> tasks = new ArrayList<>();

}
