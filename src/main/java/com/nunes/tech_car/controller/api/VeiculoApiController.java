package com.nunes.tech_car.controller.api;

import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation; // Importe as anotações
import io.swagger.v3.oas.annotations.tags.Tag; // Importe as anotações
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin("*")
public class VeiculoApiController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return veiculoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Veiculo> criar(@RequestBody Veiculo veiculo) {
        Veiculo salvo = veiculoService.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        if (!veiculoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        veiculo.setId(id);
        return ResponseEntity.ok(veiculoService.save(veiculo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!veiculoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        veiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ CORREÇÃO: usar o método COM paginação
    @GetMapping("/busca")
    public ResponseEntity<Page<Veiculo>> buscar(
            @RequestParam(required = false) String marca,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Veiculo> resultado = veiculoService.buscarPorMarcaPaginado(marca, PageRequest.of(page, size));
        return ResponseEntity.ok(resultado);
    }
}