package com.nunes.tech_car.controller;

import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "auth/login"; // Procura em src/main/resources/templates/auth/login.html
    }
}