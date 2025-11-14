package com.nunes.tech_car.config; // (Ou seu pacote de configuração)

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return; // Resposta já enviada
        }

        response.sendRedirect(targetUrl);
    }

    /**
     * Determina a URL de destino com base nas roles do usuário.
     */
    protected String determineTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Verifica se o usuário é ADMIN
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        // Verifica se é USER (mas não admin, se a lógica for exclusiva)
        boolean isUser = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));

        if (isAdmin) {
            // Admin vai para o Dashboard (página de admin)
            return "/app/dashboard";
        } else if (isUser) {
            // User vai para a lista de Clientes
            return "/app/veiculos";
        } else {
            // Fallback (caso seguro, mas improvável)
            return "/";
        }
    }
}