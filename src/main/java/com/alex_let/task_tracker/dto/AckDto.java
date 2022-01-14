package com.alex_let.task_tracker.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AckDto
{
    Boolean answer;

    //че
    public static AckDto makeDefault(Boolean answer)
    {
        return builder()
            .answer(answer)
            .build();
    }
}
