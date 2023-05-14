package com.example.testing.module.demo.student;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    public Student(String name, String email, Gender gender){
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

}
