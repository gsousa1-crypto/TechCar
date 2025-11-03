package com.nunes.tech_car.service;

import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Veiculo> findByMarca(String marca) {
        return veiculoRepository.findByMarcaContainingIgnoreCase(marca);
    }

    public List<Veiculo> findByModelo(String modelo) {
        return veiculoRepository.findByModeloContainingIgnoreCase(modelo);
    }
}