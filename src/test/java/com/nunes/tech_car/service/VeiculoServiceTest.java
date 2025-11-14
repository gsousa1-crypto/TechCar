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

import static org.junit.jupiter.api.Assertions.*; // Importa os asserts
import static org.mockito.Mockito.*; // Importa o Mockito

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

    // (Opcional) Configuração que roda antes de CADA teste
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
        // 1. ARRANGE (Organizar): O que o mock deve fazer?
        // Quando o 'veiculoRepository.findAll()' for chamado, retorne uma lista com o 'veiculoTeste'
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculoTeste));

        // 2. ACT (Agir): Execute o método que queremos testar
        List<Veiculo> resultado = veiculoService.findAll();

        // 3. ASSERT (Verificar): O resultado foi o esperado?
        assertNotNull(resultado); // A lista não pode ser nula
        assertEquals(1, resultado.size()); // O tamanho da lista deve ser 1
        assertEquals("Civic", resultado.get(0).getModelo());
    }

    /**
     * Teste para o método saveFromDTO() (Criação de Veículo)
     */
    @Test
    void testSaveFromDTO() {
        // 1. ARRANGE
        // O Mockito precisa "capturar" o objeto que o saveFromDTO cria
        // e fingir que o salvou no banco.

        // Estamos dizendo: "Quando o repository salvar QUALQUER objeto Veiculo,
        // apenas retorne o mesmo objeto que você recebeu."
        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(invocation -> {
            Veiculo v = invocation.getArgument(0);
            v.setId(99L); // Simula o banco de dados atribuindo um ID
            return v;
        });

        // 2. ACT
        // Chamamos o método de salvar (que não tem imagemUrl, usado pela API)
        Veiculo veiculoSalvo = veiculoService.saveFromDTO(veiculoDtoTeste);

        // 3. ASSERT
        assertNotNull(veiculoSalvo);
        assertEquals(99L, veiculoSalvo.getId()); // Verifica se o ID foi atribuído
        assertEquals("Ford", veiculoSalvo.getMarca()); // Verifica se a marca foi copiada do DTO
        assertEquals(Veiculo.StatusVeiculo.DISPONIVEL, veiculoSalvo.getStatus()); // Verifica se o status padrão foi aplicado

        // Verifica se o método save() do repositório foi chamado exatamente 1 vez
        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
    }
}