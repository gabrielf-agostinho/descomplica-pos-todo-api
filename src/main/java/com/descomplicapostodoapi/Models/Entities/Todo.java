package com.descomplicapostodoapi.Models.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private String title;

    @Column(length = 5000)
    @Getter
    @Setter
    private String description;

    @Column(name = "created_at")
    @Getter
    @Setter
    private Date createdAt;

    @Column(name = "updated_at")
    @Getter
    @Setter
    private Date updatedAt;

    @Column
    @Getter
    @Setter
    private boolean finished;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;
}
