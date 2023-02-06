package com.example.loginJPA.Thymeleaf.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String apellido;

    @Column(unique = true) // vamos a usar el email para que sea un dato unico
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // para llamar esta variable rol cuando queramos
    @JoinTable(
            name = "user_rol",
            joinColumns = @JoinColumn(name = "idUser", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "idRol", referencedColumnName = "id")
    )
    private List<rolUsers> rol;

    public users (String nombre, String apellido, String email, String password, List<rolUsers> rol){
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
}