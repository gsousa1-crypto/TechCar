package com.nunes.tech_car.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data // Gera Getters, Setters, etc.
public class VeiculoDTO {

    @NotBlank(message = "A marca é obrigatória")
    private String marca;

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1900, message = "Ano inválido")
    private Integer ano;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser positivo")
    private BigDecimal preco;

    private String descricao;
}