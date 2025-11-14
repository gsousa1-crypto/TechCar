package com.nunes.tech_car.config;

import com.nunes.tech_car.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // Injeta os componentes 'final'
@EnableMethodSecurity
public class SecurityConfig {

    // 1. Injetado para o UserDetailsService
    private final UsuarioRepository usuarioRepository;

    // 2. Injetado para o redirecionamento pós-login
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /**
     * Bean obrigatório para o DataLoader e para o Spring Security.
     * @Lazy(false) força o carregamento imediato.
     */
    @Bean
    @Lazy(false)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean obrigatório que busca o usuário no banco de dados durante o login.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                .map(usuario -> org.springframework.security.core.userdetails.User.builder()
                        .username(usuario.getEmail())
                        .password(usuario.getSenha())
                        .roles(usuario.getPapeis().stream()
                                .map(role -> role.replace("ROLE_", ""))
                                .toArray(String[]::new))
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    /**
     * Bean que define as regras de permissão de URL e o fluxo de login.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Permissões Públicas (CSS, JS, Imagens, Home, Login)
                        .requestMatchers(
                                "/", "/home",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()

                        // Permissões da API e Swagger (Abertas para teste)
                        .requestMatchers(
                                "/api/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"
                        ).permitAll()

                        // Permissões de Roles (Área Restrita)
                        .requestMatchers("/app/dashboard").hasRole("ADMIN")
                        .requestMatchers("/app/clientes").hasRole("USER")
                        .requestMatchers("/app/**").hasAnyRole("USER", "ADMIN")

                        // Qualquer outra requisição deve ser autenticada
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        // Usa o Handler customizado para redirecionar por Role
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        // Ao sair, volta para a página inicial (ou /login?logout se preferir)
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                // Desabilita CSRF (comum para APIs)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}