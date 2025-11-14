package com.nunes.tech_car.controller.web; // (ou onde seu HomeController estiver)

import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // 1. Importe o Model
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List; // 2. Importe a List

@Controller
@RequiredArgsConstructor // 3. Use Lombok para injeção
public class HomeController {

    private final VeiculoService veiculoService; // 4. Injete o VeiculoService

    @GetMapping("/")
    public String home(Model model) { // 5. Receba o Model

        // 6. Busque os carros em destaque
        List<Veiculo> destaques = veiculoService.findLatest3();

        // 7. Envie os carros para o HTML
        model.addAttribute("destaques", destaques);

        return "home"; // -> /templates/home.html
    }
}