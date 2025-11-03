package com.nunes.tech_car.repository;

import com.nunes.tech_car.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    // Busca por marca (ignorando maiúsculas/minúsculas)
    List<Veiculo> findByMarcaContainingIgnoreCase(String marca);

    // Busca por modelo (ignorando maiúsculas/minúsculas)
    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);

    // Busca por status
    List<Veiculo> findByStatus(Veiculo.StatusVeiculo status);
}