package com.descomplicapostodoapi.Models.DTOs.Todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class TodoPostPutDTO {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private boolean finished;

    @Getter
    @Setter
    private long userId;
}
