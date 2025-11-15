package com.nunes.tech_car.repository;

import com.nunes.tech_car.entity.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {


    //  Busca EXATA (precisa ser igual)
    List<Veiculo> findByMarca(String marca);

    //  Busca por CONTEÚDO (contém) - SEM paginação
    List<Veiculo> findByMarcaContainingIgnoreCase(String marca);

    //  Busca por CONTEÚDO - COM paginação
    Page<Veiculo> findByMarcaContainingIgnoreCase(String marca, Pageable pageable);

    //  Busca por modelo
    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);

    //  Busca por ano
    List<Veiculo> findByAno(Integer ano);

    //  Busca por faixa de preço
    List<Veiculo> findByPrecoBetween(Double minPreco, Double maxPreco);

    List<Veiculo> findTop3ByOrderByDataCriacaoDesc();
}