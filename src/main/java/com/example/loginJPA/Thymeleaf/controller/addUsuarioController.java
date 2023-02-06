package com.example.loginJPA.Thymeleaf.controller;

import com.example.loginJPA.Thymeleaf.DTO.usersDTO;
import com.example.loginJPA.Thymeleaf.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class addUsuarioController {
// https://www.youtube.com/watch?v=0wTsLRxS3gA&list=LL&index=5&t=5806s min 50
    @Autowired
    private userService userService;

    @ModelAttribute("user") // este es para el action para el form, con esto registra las variables del modeloDTO
    public usersDTO returnNewUser(){
        return new usersDTO();
    }

    // viewFormRegister Controlar las url los edpoints
    @GetMapping("")
    public String home(){
        return "registro";
    }

    @PostMapping // ("/save")
    public String saveUser(@ModelAttribute("user") usersDTO usersDTO){
        userService.saveUser(usersDTO);
        return "redirect:/registro?exito"; // mensaje de confirmacion del registro
    }
}