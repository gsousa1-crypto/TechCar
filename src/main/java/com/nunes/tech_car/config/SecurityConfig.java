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
<<<<<<< Updated upstream
@RequiredArgsConstructor // Injeta os componentes 'final'
=======
@RequiredArgsConstructor
>>>>>>> Stashed changes
@EnableMethodSecurity
public class SecurityConfig {


    private final UsuarioRepository usuarioRepository;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

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


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/", "/home",
                                "/css/**", "/js/**", "/images/**",
                                "/logo.png", "/lupa.png",
                                "/uploads/**",

                                // Caminhos do Swagger/API
                                "/api/**",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()

                        .requestMatchers("/login").permitAll()

                        // ... (Resto das suas regras: /app/**, etc.)
                        .anyRequest().authenticated()
                )
                // ... (Resto da configuração: formLogin, logout, csrf)
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}