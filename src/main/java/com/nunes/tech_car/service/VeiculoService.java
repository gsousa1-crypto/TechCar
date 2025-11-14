package com.nunes.tech_car.service;

import com.nunes.tech_car.dto.VeiculoDTO;
import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public List<Veiculo> findAll() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> findById(Long id) {
        return veiculoRepository.findById(id);
    }

    // --- Métodos para o WEB Controller (com upload) ---
    public Veiculo saveFromDTO(VeiculoDTO dto, String imagemUrl) {
        Veiculo veiculo = new Veiculo();
        mapDtoToEntity(dto, veiculo); // Copia os dados
        veiculo.setStatus(Veiculo.StatusVeiculo.DISPONIVEL);

        if (imagemUrl != null) {
            veiculo.setImagemUrl(imagemUrl);
        } else {
            veiculo.setImagemUrl("/images/car-placeholder.jpg");
        }

        return veiculoRepository.save(veiculo);
    }

    public Veiculo update(Long id, VeiculoDTO dto, String imagemUrl) {
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + id));

        mapDtoToEntity(dto, veiculo); // Copia os dados

        // Só atualiza a imagem se uma NOVA imagem foi enviada (pelo form web)
        if (imagemUrl != null) {
            veiculo.setImagemUrl(imagemUrl);
        }

        return veiculoRepository.save(veiculo);
    }

    // --- ✅ CORREÇÃO: Métodos Sobrecarragados para a API ---

    /**
     * Versão do 'saveFromDTO' para a API (sem upload de imagem).
     */
    public Veiculo saveFromDTO(VeiculoDTO dto) {
        // Chama o método principal, passando 'null' para a imagem
        return this.saveFromDTO(dto, null);
    }

    /**
     * Versão do 'update' para a API (sem upload de imagem).
     */
    public Veiculo update(Long id, VeiculoDTO dto) {
        // Chama o método principal, passando 'null' para a imagem
        // (Assim, a API não sobrescreve a imagem existente)
        return this.update(id, dto, null);
    }

    // --- Métodos Auxiliares ---

    // Helper para copiar DTO -> Entidade
    private void mapDtoToEntity(VeiculoDTO dto, Veiculo veiculo) {
        veiculo.setMarca(dto.getMarca());
        veiculo.setModelo(dto.getModelo());
        veiculo.setAno(dto.getAno());
        veiculo.setPreco(dto.getPreco());
        veiculo.setDescricao(dto.getDescricao());
    }

    public void deleteById(Long id) {
        if (!veiculoRepository.existsById(id)) {
            throw new EntityNotFoundException("Veículo não encontrado com ID: " + id);
        }
        veiculoRepository.deleteById(id);
    }

    public Veiculo save(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    // --- Métodos de Busca ---

    public Page<Veiculo> buscarPorMarcaPaginado(String marca, Pageable pageable) {
        if (marca == null || marca.isBlank()) {
            return veiculoRepository.findAll(pageable);
        }
        return veiculoRepository.findByMarcaContainingIgnoreCase(marca, pageable);
    }

    public List<Veiculo> findByMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            return veiculoRepository.findAll();
        }
        return veiculoRepository.findByMarcaContainingIgnoreCase(marca);
    }

    public List<Veiculo> findLatest3() {
        return veiculoRepository.findTop3ByOrderByDataCriacaoDesc();
    }
}