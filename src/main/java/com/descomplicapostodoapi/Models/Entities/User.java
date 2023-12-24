package com.descomplicapostodoapi.Models.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String email;

    @Column(nullable = false)
    @Getter
    @Setter
    private String password;

    @Column(nullable = false)
    @Getter
    @Setter
    private String name;

    @Column
    @Getter
    @Setter
    private String refresh;

    @Column(name = "expires_at")
    @Getter
    @Setter
    private Date expiresAt;

    @OneToMany(mappedBy = "user")
    @Getter
    @Setter
    private List<Todo> todos = new ArrayList<>();
}
