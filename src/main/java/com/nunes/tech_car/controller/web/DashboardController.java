package com.nunes.tech_car.controller.web;

import com.nunes.tech_car.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private VeiculoService veiculoService;

    // Mapeia a URL http://localhost:8080/app/dashboard
    @GetMapping("/app/dashboard")
    public String showDashboard(Model model) {

        // **LOGICA PARA PREENCHER OS CARDS DO SEU TEMPLATE**
        long totalVeiculos = veiculoService.findAll().size();
        // Exemplo: assumindo que todos os veículos são disponíveis por enquanto
        long veiculosDisponiveis = totalVeiculos;

        model.addAttribute("totalVeiculos", totalVeiculos);
        model.addAttribute("veiculosDisponiveis", veiculosDisponiveis);
        // FIM DA LOGICA

        // Retorna o template que o Thymeleaf deve buscar em:
        // src/main/resources/templates/app/dashboard.html
        return "app/dashboard";
    }
}