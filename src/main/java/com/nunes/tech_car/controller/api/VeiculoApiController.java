package com.nunes.tech_car.controller.api;

import com.nunes.tech_car.dto.VeiculoDTO;
import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin("*")
@Tag(name = "Veículos", description = "Operações de CRUD para veículos")
@RequiredArgsConstructor
public class VeiculoApiController {

    private final VeiculoService veiculoService;

    @GetMapping
    @Operation(summary = "Lista todos os veículos (não paginado)")
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um veículo pelo ID")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return veiculoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cria um novo veículo")
    public ResponseEntity<Veiculo> criar(@RequestBody @Valid VeiculoDTO dto) { // Usa DTO
        Veiculo salvo = veiculoService.saveFromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um veículo existente")
    public ResponseEntity<Veiculo> atualizar(@PathVariable Long id, @RequestBody @Valid VeiculoDTO dto) { // Usa DTO
        try {
            Veiculo atualizado = veiculoService.update(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um veículo pelo ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            veiculoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/busca")
    @Operation(summary = "Busca veículos por marca (com paginação)")
    public ResponseEntity<Page<Veiculo>> buscar(
            @RequestParam(required = false, defaultValue = "") String marca,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<Veiculo> resultado = veiculoService.buscarPorMarcaPaginado(marca, pageable);
        return ResponseEntity.ok(resultado);
    }
}