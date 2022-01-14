package com.alex_let.task_tracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
//Generates getters for all fields, a useful toString method,
// and hashCode and equals implementations that check all non-transient fields.
// Will also generate setters for all non-final fields, as well as a constructor.
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectDto
{
    @NonNull Long id;

    @NonNull String name;

    @NonNull
    @JsonProperty("created_at") //имя поля в json`e
    Instant createdAt;

    @NonNull
    @JsonProperty("updated_at") //имя поля в json`e
    Instant updatedAt;
}
