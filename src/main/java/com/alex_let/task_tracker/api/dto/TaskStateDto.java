package com.alex_let.task_tracker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.Instant;
import java.util.List;

@Data
//Generates getters for all fields, a useful toString method,
// and hashCode and equals implementations that check all non-transient fields.
// Will also generate setters for all non-final fields, as well as a constructor.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskStateDto
{

    @NonNull Long id;

    @NonNull String name;

    @NonNull Long ordinal; //номер для сортировки

    @NonNull
    @JsonProperty("created_at")
    Instant createdAt = Instant.now();

    @NonNull List<TaskDto> tasks;
}
