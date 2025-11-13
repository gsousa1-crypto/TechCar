package com.nunes.tech_car.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "auth/login"; // Procura em src/main/resources/templates/auth/login.html
    }
}