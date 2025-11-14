package com.nunes.tech_car.controller.web;

import com.nunes.tech_car.dto.VeiculoDTO;
import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.service.FileStorageService;
import com.nunes.tech_car.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page; // Import para Page
import org.springframework.data.domain.Pageable; // Import para Pageable
import org.springframework.data.web.PageableDefault; // Import para PageableDefault
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/app/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;
    private final FileStorageService fileStorageService;

    /**
     * Lista veículos com busca E paginação.
     */
    @GetMapping
    public String listarVeiculos(
            @RequestParam(required = false, defaultValue = "") String busca,
            @PageableDefault(size = 6, page = 0) Pageable pageable, // Define o padrão: 6 itens por página
            Model model) {

        // Usa o método de serviço que busca e pagina
        Page<Veiculo> veiculosPage = veiculoService.buscarPorMarcaPaginado(busca, pageable);

        model.addAttribute("veiculosPage", veiculosPage); // Envia a PÁGINA (não uma Lista)
        model.addAttribute("busca", busca);
        return "veiculos/list";
    }

    /**
     * Mostra o formulário para criar um novo veículo (Admin).
     */
    @GetMapping("/novo")
    public String mostrarFormNovo(Model model) {
        model.addAttribute("veiculoDTO", new VeiculoDTO());
        model.addAttribute("veiculoId", null);
        return "veiculos/form";
    }

    /**
     * Mostra o formulário para editar um veículo (Admin).
     */
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

    /**
     * Salva (novo) ou Atualiza (existente) um veículo (Admin).
     */
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

    /**
     * Exclui um veículo (Admin).
     */
    @GetMapping("/{id}/excluir")
    public String excluirVeiculo(@PathVariable Long id) {
        veiculoService.deleteById(id);
        return "redirect:/app/veiculos";
    }

    /**
     * Mostra a página de detalhes (pode ser usado por Admin e User).
     */
    @GetMapping("/{id}")
    public String verDetalhes(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
        model.addAttribute("veiculo", veiculo);
        return "veiculos/detalhes";
    }
}