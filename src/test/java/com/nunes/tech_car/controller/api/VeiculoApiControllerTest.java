package com.nunes.tech_car.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nunes.tech_car.dto.VeiculoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

// Importes estáticos
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Garante que o teste não salve dados permanentemente no DB
class VeiculoApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Teste para: GET /api/veiculos
     */
    @Test
    void testListarTodos() throws Exception {
        mockMvc.perform(get("/api/veiculos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Verifica se a lista tem tamanho (size) maior que (greaterThan) 0
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                // Verifica se na lista de 'marca' ($.*.marca), existe (hasItem) o valor "Toyota"
                // (Isso assume que seu DataLoader criou um Toyota)
                .andExpect(jsonPath("$.*.marca", hasItem("Toyota")));
    }

    /**
     * Teste para: POST /api/veiculos
     */
    @Test
    void testCriarVeiculo() throws Exception {
        // 1. ARRANGE
        VeiculoDTO novoVeiculoDTO = new VeiculoDTO();
        novoVeiculoDTO.setMarca("Tesla");
        novoVeiculoDTO.setModelo("Model Y");
        novoVeiculoDTO.setAno(2024);
        novoVeiculoDTO.setPreco(new BigDecimal("210000.00"));
        novoVeiculoDTO.setDescricao("Veículo elétrico novo");

        String dtoComoJson = objectMapper.writeValueAsString(novoVeiculoDTO);

        // 2. ACT & 3. ASSERT
        mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoComoJson))

                .andExpect(status().isCreated()) // Espera Status 201 CREATED
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.marca").value("Tesla"))
                .andExpect(jsonPath("$.modelo").value("Model Y"));
    }
}