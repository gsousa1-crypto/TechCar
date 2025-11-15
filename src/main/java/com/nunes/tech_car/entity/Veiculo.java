package com.nunes.tech_car.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
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

    private BigDecimal preco;

    @Column(length = 1000)
    private String descricao;

    private String imagemUrl;

    @Enumerated(EnumType.STRING)
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
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private Usuario usuario;
}