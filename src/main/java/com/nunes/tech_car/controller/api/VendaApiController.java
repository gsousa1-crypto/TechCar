package com.nunes.tech_car.controller.api;

import com.nunes.tech_car.entity.Usuario;
import com.nunes.tech_car.entity.Venda;
import com.nunes.tech_car.repository.UsuarioRepository;
import com.nunes.tech_car.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Para segurança de método
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal; // Para pegar o usuário logado
import java.util.List;

@RestController
@RequestMapping("/api/vendas")
@Tag(name = "Vendas", description = "Endpoints para consultar vendas realizadas")
@RequiredArgsConstructor
public class VendaApiController {

    private final VendaService vendaService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Endpoint SÓ PARA ADMINS: Lista todas as vendas de todos os usuários.
     */
    @GetMapping
    @Operation(summary = "Lista TODAS as vendas (Somente ADMIN)")
    @PreAuthorize("hasRole('ADMIN')") // Segurança a nível de método
    public ResponseEntity<List<Venda>> listarTodasAsVendas() {
        // (Você pode precisar adicionar um 'findAll()' no VendaService/Repository)
        // return ResponseEntity.ok(vendaService.findAll());
        return ResponseEntity.ok().build(); // Implementar se necessário
    }

    /**
     * Endpoint PARA USERS: Lista apenas as compras do usuário logado.
     */
    @GetMapping("/minhas-compras")
    @Operation(summary = "Lista apenas as compras do usuário autenticado (USER)")
    @PreAuthorize("hasRole('USER')") // Segurança a nível de método
    public ResponseEntity<List<Venda>> listarMinhasCompras(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).build(); // Não autorizado
        }

        Usuario usuarioLogado = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        List<Venda> minhasVendas = vendaService.findVendasByUsuarioId(usuarioLogado.getId());

        return ResponseEntity.ok(minhasVendas);
    }
}