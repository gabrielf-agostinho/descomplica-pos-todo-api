package com.descomplicapostodoapi.Models.DTOs.Todo;

import com.descomplicapostodoapi.Models.DTOs.User.UserGetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class TodoGetDTO {
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
    private Date createdAt;

    @Getter
    @Setter
    private Date updatedAt;

    @Getter
    @Setter
    private boolean finished;

    @Getter
    @Setter
    private UserGetDTO user;
}
