package com.cydeo.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @OneToOne
    private Role role;

    private boolean isEnabled;


}
