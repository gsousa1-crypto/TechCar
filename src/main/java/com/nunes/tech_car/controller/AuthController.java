package com.nunes.tech_car.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/app/dashboard")
    public String dashboard() {
        return "app/dashboard";
    }
}