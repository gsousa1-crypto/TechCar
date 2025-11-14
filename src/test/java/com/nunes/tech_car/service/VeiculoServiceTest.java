package com.nunes.tech_car.service;

import com.nunes.tech_car.dto.VeiculoDTO;
import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Define que vamos usar Mockito nesta classe de teste
 */
@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock // 1. Cria um "mock" (uma versão falsa) do repositório
    private VeiculoRepository veiculoRepository;

    @InjectMocks // 2. Cria uma instância real do VeiculoService e injeta o mock acima
    private VeiculoService veiculoService;

    private Veiculo veiculoTeste;
    private VeiculoDTO veiculoDtoTeste;

    // Configuração que roda antes de CADA teste
    @BeforeEach
    void setUp() {
        // Cria um DTO para usar nos testes de salvar/atualizar
        veiculoDtoTeste = new VeiculoDTO();
        veiculoDtoTeste.setMarca("Ford");
        veiculoDtoTeste.setModelo("Ranger");
        veiculoDtoTeste.setAno(2023);
        veiculoDtoTeste.setPreco(new BigDecimal("150000.00"));

        // Cria uma entidade para usar nos testes de busca
        veiculoTeste = new Veiculo();
        veiculoTeste.setId(1L);
        veiculoTeste.setMarca("Honda");
        veiculoTeste.setModelo("Civic");
    }

    /**
     * Teste para o método findAll()
     */
    @Test
    void testFindAll() {
        // 1. ARRANGE (Organizar)
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculoTeste));

        // 2. ACT (Agir)
        List<Veiculo> resultado = veiculoService.findAll();

        // 3. ASSERT (Verificar)
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Civic", resultado.get(0).getModelo()); // Corrigido
    }

    /**
     * Teste para o método saveFromDTO() (Criação de Veículo)
     */
    @Test
    void testSaveFromDTO() {
        // 1. ARRANGE
        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(invocation -> {
            Veiculo v = invocation.getArgument(0);
            v.setId(99L); // Simula o banco de dados atribuindo um ID
            return v;
        });

        // 2. ACT
        Veiculo veiculoSalvo = veiculoService.saveFromDTO(veiculoDtoTeste);

        // 3. ASSERT
        assertNotNull(veiculoSalvo);
        assertEquals(99L, veiculoSalvo.getId());
        assertEquals("Ford", veiculoSalvo.getMarca());
        assertEquals(Veiculo.StatusVeiculo.DISPONIVEL, veiculoSalvo.getStatus());

        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
    }
}