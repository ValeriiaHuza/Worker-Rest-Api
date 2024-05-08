package com.springbootcourse.worker_api.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "\"position\"", length = 100)
    private String position;

    @OneToMany(mappedBy = "worker",
    cascade = CascadeType.ALL)
    private Set<PhoneNumber> phoneNumbers = new LinkedHashSet<>();

}