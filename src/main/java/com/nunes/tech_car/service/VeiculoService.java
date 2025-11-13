package com.nunes.tech_car.service;

import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> findById(Long id) {
        return veiculoRepository.findById(id);
    }

    public Veiculo save(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    public void deleteById(Long id) {
        veiculoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return veiculoRepository.existsById(id);
    }

    // ✅ PARA O CONTROLLER WEB (sem paginação)
    public List<Veiculo> buscarPorMarca(String marca) {
        if (marca != null && !marca.trim().isEmpty()) {
            return veiculoRepository.findByMarcaContainingIgnoreCase(marca);
        }
        return veiculoRepository.findAll();
    }

    // ✅ PARA A API (COM paginação)
    public Page<Veiculo> buscarPorMarcaPaginado(String marca, Pageable pageable) {
        if (marca != null && !marca.trim().isEmpty()) {
            return veiculoRepository.findByMarcaContainingIgnoreCase(marca, pageable);
        }
        return veiculoRepository.findAll(pageable);
    }
}