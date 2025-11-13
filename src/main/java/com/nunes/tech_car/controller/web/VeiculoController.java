package com.nunes.tech_car.controller.web;

import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/app/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public String listarVeiculos(@RequestParam(required = false) String search, Model model) {
        List<Veiculo> veiculos;

        if (search != null && !search.trim().isEmpty()) {
            // ✅ CORRETO: buscar por marca
            veiculos = veiculoService.buscarPorMarca(search);
        } else {
            // ✅ CORRETO: buscar todos
            veiculos = veiculoService.findAll();
        }

        model.addAttribute("veiculos", veiculos);
        model.addAttribute("search", search);
        return "veiculos/list";
    }

    // MÉTODOS ADICIONAIS DO CRUD:

    @GetMapping("/novo")
    public String mostrarFormNovo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        return "veiculos/form";
    }

    @PostMapping
    public String salvarVeiculo(@ModelAttribute Veiculo veiculo) {
        veiculoService.save(veiculo);
        return "redirect:/app/veiculos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        model.addAttribute("veiculo", veiculo);
        return "veiculos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluirVeiculo(@PathVariable Long id) {
        veiculoService.deleteById(id);
        return "redirect:/app/veiculos";
    }
}