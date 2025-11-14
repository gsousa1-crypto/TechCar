package com.nunes.tech_car.service;

import com.nunes.tech_car.entity.Usuario;
import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.entity.Venda;
import com.nunes.tech_car.repository.UsuarioRepository;
import com.nunes.tech_car.repository.VeiculoRepository;
import com.nunes.tech_car.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Injeta os repositórios via construtor
public class VendaService {

    // Repositórios necessários para a lógica de Venda
    private final VendaRepository vendaRepository;
    private final VeiculoRepository veiculoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Processa a compra de um veículo por um usuário.
     *
     * @param usuarioId ID do usuário que está comprando.
     * @param veiculoId ID do veículo a ser comprado.
     * @return A entidade Venda que foi salva.
     */
    public Venda realizarVenda(Long usuarioId, Long veiculoId) {

        // 1. Encontra o usuário que está comprando
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        // 2. Encontra o veículo que está sendo comprado
        Veiculo veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + veiculoId));

        // 3. (Opcional) Lógica de Negócio: Verifica se o veículo está disponível
        if (veiculo.getStatus() != Veiculo.StatusVeiculo.DISPONIVEL) {
            throw new IllegalStateException("Este veículo não está disponível para venda.");
        }

        // 4. Cria a nova Venda
        Venda novaVenda = new Venda(usuario, veiculo);

        // 5. Atualiza o status do veículo para VENDIDO
        veiculo.setStatus(Veiculo.StatusVeiculo.VENDIDO);
        veiculoRepository.save(veiculo);

        // 6. Salva a Venda no banco de dados
        return vendaRepository.save(novaVenda);
    }

    /**
     * Encontra todas as vendas de um usuário específico.
     * (Útil se você quiser criar uma página "Minhas Compras")
     */
    public List<Venda> findVendasByUsuarioId(Long usuarioId) {
        return vendaRepository.findByUsuarioId(usuarioId);
    }
}