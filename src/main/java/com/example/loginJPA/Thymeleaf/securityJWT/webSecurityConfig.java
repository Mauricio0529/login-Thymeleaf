package com.example.loginJPA.Thymeleaf.securityJWT;

import com.example.loginJPA.Thymeleaf.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
    * CONFIGURACION DEL SECURITY JWT *
*/

@Configuration
@EnableWebSecurity
public class webSecurityConfig extends WebSecurityConfigurerAdapter {

    @Lazy
    @Autowired
    private userService userService;

    /* Para encriptar el password */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*  */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // estamos estableciendo el UserDetailsService, le pasamos el servicio
        /* Hacemos el extends en la interface de userService.
        * setUserDetailsService - obtener y mostrar datos de un usuario*/
        authProvider.setUserDetailsService(userService);
        // le pasamos el password cifrado
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider; // retorna los datos de un usuario
    }

    /*
        validar si los datos de la autentificacion (authenticationProvider)
        son validos con base a los datos de la base de datos
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /*
        configurar los permisos
        NOTA: estudiar esta configuracion, no la entendi muy bien
        Minuto: 1:02:00
        https://www.youtube.com/watch?v=0wTsLRxS3gA&list=LL&index=6&t=5806s
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(
                "/registro**", // dar perminos a las rutas y archivos
                "/js/**",
                "/css/**",
                "/img/**").permitAll() // permitir a t o d o
                .anyRequest().authenticated() // cualquier peticion la autenticamos
                .and()
                .formLogin()
                .loginPage("/login") // pantalla principal donde se carga por primera vez. nombre del archivo login.html
                .permitAll() // en la parte de login le permitimos tod_o
                .and()
                .logout() // para cerrar session
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // la ruta cuando quiero hacer un logout
                .logoutSuccessUrl("/login?logout")  // a la hora de hacer login vamos mostrar un mensaje
                .permitAll();
    }
}