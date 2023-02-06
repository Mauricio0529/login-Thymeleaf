package com.example.loginJPA.Thymeleaf.service;

import com.example.loginJPA.Thymeleaf.DTO.usersDTO;
import com.example.loginJPA.Thymeleaf.models.rolUsers;
import com.example.loginJPA.Thymeleaf.models.users;
import com.example.loginJPA.Thymeleaf.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements userService {

    /* para encriptar la contraseña */
    @Autowired
    private BCryptPasswordEncoder PasswordEncoder;

    @Lazy // https://stackoverflow.com/questions/41608705/the-dependencies-of-some-of-the-beans-in-the-application-context-form-a-cycle
    @Autowired
    private userRepository userRepository;

    @Override
    public users saveUser(usersDTO usersDTO) {
        // hacemos el mapeo del DTO y la entidad, seria mejor hacer los mapeadores aparte de los servicios
        // se puede hacer un mapedo de los DTO por medio de ModelMapper o mapstruct
        /* ej:
        ModelMapper mM = new ModelMapper();
        usersDTO userDTO = mM.map(users, usersDTO.class);
        * */
        users user = new users(
                usersDTO.getNombre(), usersDTO.getApellido(),
                usersDTO.getEmail(), PasswordEncoder.encode(usersDTO.getPassword()), Arrays.asList(new rolUsers("ROLE_USER"))
                /*
                Arrays.asList(new rolUsers("ROLE_USER")- esta agregando un dato a la lista de rol,
                ya que ese dato el usuario no lo ingresa si no se registra el rol por defecto
                registrar un rol por defecto al momento de registrar un usuario nuevo
                * */
        );
        return userRepository.save(user);
    }

    // aqui carga el usuario con sus datos del UserDetails de la clase webSecurityConfig
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        users user = userRepository.findByEmail(email); // username esto no se usa en el html
        if(user == null){
            // tengo que estudiar las exception de (throw)
             throw new UsernameNotFoundException("Usuario o contraseña invalido");
        }
        // este User no es del modelo si no del userDetails de spring security
        return new User(user.getEmail(), user.getPassword(), mapearAutoridadesRoles(user.getRol()));
    }

    // vamos a mapear los roles.
    // ? esto es un metodo general
    private List<? extends GrantedAuthority> mapearAutoridadesRoles(List<rolUsers> rol){
        return rol
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getRol())) // se obtiene el rol
                .collect(Collectors.toList());
    }
}

/*
https://www.youtube.com/watch?v=0wTsLRxS3gA&list=LL&index=4&t=5806s
https://somospnt.com/blog/162-maneja-tus-usuarios-y-sus-roles-con-spring-security
 */