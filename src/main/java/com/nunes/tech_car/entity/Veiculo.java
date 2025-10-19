package com.nunes.tech_car.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "veiculos")  // CORRIGIDO
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "veiculo_seq")
    @SequenceGenerator(name = "veiculo_seq", sequenceName = "veiculo_seq", allocationSize = 1)  // CORRIGIDO
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    private Integer ano;

    private BigDecimal preco;  // CORRIGIDO

    @Column(length = 1000)
    private String descricao;  // CORRIGIDO

    private String imagemUrl;  // CORRIGIDO

    @Enumerated(EnumType.STRING)  // CORRIGIDO
    private StatusVeiculo status;

    private LocalDateTime dataCriacao;

    // Enum
    public enum StatusVeiculo {
        DISPONIVEL, VENDIDO, RESERVADO
    }

    // Construtor
    public Veiculo() {
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusVeiculo.DISPONIVEL;
    }
}