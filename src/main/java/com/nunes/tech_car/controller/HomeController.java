// Crie/Edite o arquivo HomeController.java
package com.nunes.tech_car.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Este método irá servir o template home.html quando o usuário acessar a raiz (/).
    // A página home.html deve ser pública (permitAll), como configurado no SecurityConfig.
    @GetMapping("/")
    public String home() {
        // Isso fará o Thymeleaf procurar em src/main/resources/templates/home.html
        return "home";
    }
}