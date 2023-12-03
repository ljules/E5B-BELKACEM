package com.example.springaventure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    /**
     * Cette méthode fournit un bean `PasswordEncoder` pour l'application.
     *
     * @return Une instance de l'implémentation BCryptPasswordEncoder pour l'encodage des mots de passe.
     *         Cette instance sera utilisée pour encoder et décoder les mots de passe de l'application.
     */
    @Bean
    fun getEncoder(): PasswordEncoder? {
        // Retourner une instance de BCryptPasswordEncoder pour l'encodage sécurisé des mots de passe
        return BCryptPasswordEncoder()
    }

    /**
     * Configure la chaîne de filtres de sécurité pour l'application.
     *
     * @param httpSecurity La configuration de sécurité HTTP à personnaliser.
     * @return Une instance de `SecurityFilterChain` configurée pour l'application.
     * @throws Exception En cas d'erreur lors de la configuration de la sécurité.
     */
    @Bean
    @Throws(Exception::class)
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain? {
        return httpSecurity
            // Désactiver CSRF pour simplifier la configuration dans cet exemple
            .csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }

            // Configuration du formulaire de connexion
            .formLogin { form: FormLoginConfigurer<HttpSecurity?> ->
                form
                    .loginPage("/login").defaultSuccessUrl("/accueil",true).failureUrl("/login?error=true")
                    .permitAll()
            }

            // Configuration du mécanisme de déconnexion
            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
                logout
                    .logoutUrl("/logout")
                    .permitAll()
            }

            // Configuration des autorisations pour les différentes URL
            .authorizeHttpRequests { auth ->
                auth
                    // Autoriser l'accès public à certaines URL
                    .requestMatchers("/accueil", "/inscription", "/webjars/**", "/login", "/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()
                    // Autoriser l'accès pour les utilisateurs avec le rôle "admin" à /admin/**
                    .requestMatchers("/admin/**").hasAuthority("admin")
                    // Autoriser l'accès pour les utilisateurs avec le rôle "joueur" à /joueur/**
                    .requestMatchers("/joueur/**").hasAuthority("joueur")
                    // Toutes les autres requêtes doivent être authentifiées
                    .anyRequest().authenticated()
            }

            // Construire et retourner la chaîne de filtres de sécurité configurée
            .build()
    }
}