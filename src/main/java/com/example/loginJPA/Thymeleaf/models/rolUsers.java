package com.example.loginJPA.Thymeleaf.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "rolUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class rolUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String rol;

    public rolUsers(String rol) { this.rol = rol; }
}