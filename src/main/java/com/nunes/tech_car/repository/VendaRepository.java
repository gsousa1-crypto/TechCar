package com.nunes.tech_car.repository;

import com.nunes.tech_car.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    // Encontra todas as vendas de um usuário específico
    List<Venda> findByUsuarioId(Long usuarioId);

}