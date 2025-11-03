package com.nunes.tech_car.controller;

import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    // Lista todos os veículos (Mapeia para /app/veiculos)
    @GetMapping
    public String listarVeiculos(@RequestParam(required = false) String search, Model model) {
        List<Veiculo> veiculos;

        if (search != null && !search.trim().isEmpty()) {
            veiculos = veiculoService.findByMarca(search);
        } else {
            veiculos = veiculoService.findAll();
        }

        model.addAttribute("veiculos", veiculos);
        model.addAttribute("search", search);
        return "veiculos/list"; // Procura em src/main/resources/templates/veiculos/list.html
    }

    // Formulário para novo veículo (Mapeia para /app/veiculos/novo)
    @GetMapping("/novo")
    public String mostrarFormularioNovo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        return "veiculos/form"; // Procura em src/main/resources/templates/veiculos/form.html
    }

    // Salva um veículo (POST em /app/veiculos)
    @PostMapping
    public String salvarVeiculo(@ModelAttribute Veiculo veiculo) {
        veiculoService.save(veiculo);
        return "redirect:/app/veiculos";
    }

    // Formulário para editar veículo (Mapeia para /app/veiculos/{id}/editar)
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Veiculo> veiculo = veiculoService.findById(id);
        if (veiculo.isPresent()) {
            model.addAttribute("veiculo", veiculo.get());
            return "veiculos/form";
        }
        return "redirect:/app/veiculos";
    }

    // Exclui um veículo (Mapeia para /app/veiculos/{id}/excluir)
    @GetMapping("/{id}/excluir")
    public String excluirVeiculo(@PathVariable Long id) {
        veiculoService.deleteById(id);
        return "redirect:/app/veiculos";
    }

    // Vê detalhes de um veículo (Mapeia para /app/veiculos/{id})
    @GetMapping("/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        Optional<Veiculo> veiculo = veiculoService.findById(id);
        if (veiculo.isPresent()) {
            model.addAttribute("veiculo", veiculo.get());
            return "veiculos/detalhes"; // Procura em src/main/resources/templates/veiculos/detalhes.html
        }
        return "redirect:/app/veiculos";
    }
}