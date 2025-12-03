package com.senai.conta_bancaria_spring.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas (login, swagger, etc)
                        .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // --- Cliente Controller ---
                        .requestMatchers(HttpMethod.POST, "/api/cliente").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/cliente").hasRole("ADMIN")
                        .requestMatchers("/api/cliente/cpf/**").hasAnyRole("ADMIN", "CLIENTE")

                        // --- Conta Controller ---
                        // Funções de Gerenciamento (ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/conta").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/conta/{numeroConta}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/conta/{numeroDaConta}").hasRole("ADMIN")

                        // Funções de Visualização (ADMIN ou CLIENTE dono)
                        .requestMatchers(HttpMethod.GET, "/api/conta/{numeroDaConta}").hasAnyRole("ADMIN", "CLIENTE")

                        // ***** ESTA É A MUDANÇA PRINCIPAL *****
                        // Funções de Operação (Saque, Depósito, etc)
                        // Apenas o CLIENTE pode acessar estas rotas
                        .requestMatchers(HttpMethod.POST, "/api/conta/{numeroConta}/**").hasRole("CLIENTE")

                                .requestMatchers(HttpMethod.POST, "/api/pagamentos/**").hasRole("CLIENTE")

                                .requestMatchers(HttpMethod.POST, "/api/taxas").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/taxas").hasAnyRole("ADMIN", "CLIENTE")

                        .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(usuarioDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}