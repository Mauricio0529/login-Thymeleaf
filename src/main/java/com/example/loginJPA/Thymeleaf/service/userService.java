package com.example.loginJPA.Thymeleaf.service;

import com.example.loginJPA.Thymeleaf.DTO.usersDTO;
import com.example.loginJPA.Thymeleaf.models.users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/* Erenamos la interface UserDetailsService de la clase de webSecurityConfig en la Authentication.
* el UserDetailsService, este nos ayuda en la busqueda de un usuario por medio de un email,
* y se va a obtener sus datos y con la informacion obtenida puedo obtener sus roles, etc.
* */

public interface userService extends UserDetailsService {
    /*
        CREAMOS METODOS PARA IMPLEMENTARLOS EN LA CLASE SERVICE
    */
    public users saveUser(usersDTO usersDTO);
    // public usersDTO saveUserDTO(users users);
}