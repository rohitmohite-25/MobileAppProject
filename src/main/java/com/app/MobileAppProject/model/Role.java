package com.app.MobileAppProject.model;

import jakarta.persistence.*;
//import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @Column(nullable = false, unique = true)
   // @NotEmpty
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
