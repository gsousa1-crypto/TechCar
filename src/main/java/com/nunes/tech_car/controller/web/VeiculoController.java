package com.nunes.tech_car.controller.web;

import com.nunes.tech_car.dto.VeiculoDTO;
import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.service.FileStorageService;
import com.nunes.tech_car.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort; // Importe o Sort
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;

@Controller
@RequestMapping("/app/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public String listarVeiculos(
            @RequestParam(required = false, defaultValue = "") String busca,
            // 1. A ordenação padrão (preco, ASC) é aplicada na primeira visita
            @PageableDefault(size = 6, page = 0, sort = "preco", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {

        Page<Veiculo> veiculosPage = veiculoService.buscarPorMarcaPaginado(busca, pageable);

        model.addAttribute("veiculosPage", veiculosPage);
        model.addAttribute("busca", busca);

        // 2. ✅ LÓGICA DE EXTRAÇÃO DE SORT (MAIS SEGURA)
        // Pega a primeira ordenação do pageable, ou usa "preco" como padrão se estiver VAZIO.
        Sort.Order sortOrder = pageable.getSort().isSorted() ?
                pageable.getSort().get().findFirst().orElse(Sort.Order.asc("preco")) :
                Sort.Order.asc("preco");

        model.addAttribute("sortField", sortOrder.getProperty());
        model.addAttribute("sortDir", sortOrder.getDirection().name());

        return "veiculos/list";
    }

    // ... (Restante dos métodos: /novo, /editar, /salvar, /excluir, /id) ...
    // (O código abaixo é o mesmo que você já tinha)

    @GetMapping("/novo")
    public String mostrarFormNovo(Model model) {
        model.addAttribute("veiculoDTO", new VeiculoDTO());
        model.addAttribute("veiculoId", null);
        return "veiculos/form";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormEditar(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        VeiculoDTO dto = new VeiculoDTO();
        dto.setMarca(veiculo.getMarca());
        dto.setModelo(veiculo.getModelo());
        dto.setAno(veiculo.getAno());
        dto.setPreco(veiculo.getPreco());
        dto.setDescricao(veiculo.getDescricao());

        model.addAttribute("veiculoDTO", dto);
        model.addAttribute("veiculoId", id);
        return "veiculos/form";
    }

    @PostMapping("/salvar")
    public String salvarOuAtualizarVeiculo(
            @RequestParam(value = "id", required = false) Long id,
            @ModelAttribute("veiculoDTO") @Valid VeiculoDTO dto,
            BindingResult result,
            @RequestParam("imagemFile") MultipartFile imagemFile,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("veiculoId", id);
            return "veiculos/form";
        }

        String imagemUrl = null;
        if (imagemFile != null && !imagemFile.isEmpty()) {
            imagemUrl = fileStorageService.store(imagemFile);
        }

        if (id == null) {
            veiculoService.saveFromDTO(dto, imagemUrl);
        } else {
            veiculoService.update(id, dto, imagemUrl);
        }

        return "redirect:/app/veiculos";
    }

    @GetMapping("/{id}/excluir")
    public String excluirVeiculo(@PathVariable Long id) {
        veiculoService.deleteById(id);
        return "redirect:/app/veiculos";
    }

    @GetMapping("/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        model.addAttribute("veiculo", veiculo);
        return "veiculos/detalhes";
    }
}