package com.nunes.tech_car.controller.web;

import com.nunes.tech_car.entity.Usuario;
import com.nunes.tech_car.repository.UsuarioRepository;
import com.nunes.tech_car.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal; // Usado para pegar o usuário logado

@Controller
@RequestMapping("/app")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;
    private final UsuarioRepository usuarioRepository;

    /**
     * Endpoint que o ROLE_USER chama ao clicar em "Comprar".
     */
    @GetMapping("/comprar/{veiculoId}")
    public String comprarVeiculo(@PathVariable Long veiculoId,
                                 Principal principal, // Injeta o usuário logado
                                 RedirectAttributes redirectAttributes) {

        if (principal == null) {
            // Segurança extra, embora o Spring Security deva bloquear
            return "redirect:/login";
        }

        try {
            // 1. Pega o email (username) do usuário logado
            String userEmail = principal.getName();

            // 2. Busca o objeto Usuario completo pelo email
            Usuario usuarioLogado = usuarioRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            // 3. Chama o serviço de venda
            vendaService.realizarVenda(usuarioLogado.getId(), veiculoId);

            // 4. Envia uma mensagem de sucesso para a próxima página
            redirectAttributes.addFlashAttribute("successMessage", "Veículo comprado com sucesso!");

        } catch (Exception e) {
            // Se algo der errado (ex: veículo já vendido), envia erro
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao comprar veículo: " + e.getMessage());
        }

        // Retorna o usuário para a lista de veículos
        return "redirect:/app/veiculos";
    }
}