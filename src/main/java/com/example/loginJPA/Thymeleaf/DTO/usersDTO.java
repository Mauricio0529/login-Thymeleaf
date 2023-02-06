package com.example.loginJPA.Thymeleaf.DTO;
import lombok.Data;
import java.io.Serializable;

@Data
public class usersDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    // DTO es un cojo => objetos planos
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}