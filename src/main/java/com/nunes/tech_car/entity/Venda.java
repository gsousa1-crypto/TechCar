package com.nunes.tech_car.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_vendas")
@Getter
@Setter
@NoArgsConstructor // <- Construtor vazio (para o JPA)
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Column(name = "preco_final", nullable = false)
    private BigDecimal precoFinal;

    @CreationTimestamp
    @Column(name = "data_venda")
    private LocalDateTime dataVenda;

    // ✅ ADICIONE ESTE CONSTRUTOR ABAIXO ✅
    /**
     * Construtor usado pelo VendaService para criar uma nova venda.
     */
    public Venda(Usuario usuario, Veiculo veiculo) {
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.precoFinal = veiculo.getPreco(); // Pega o preço do veículo
    }
}