package it.uniroma3.siw.torneo.config;

import jakarta.servlet.http.HttpServletRequest;
import it.uniroma3.siw.torneo.service.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer; // IMPORTANTE: aggiungi questo import!
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpMethod;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(corsConfigurationSource()))

                // 2. Disabilitiamo il controllo CSRF solo per le chiamate REST (React)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/registrazione", "/login").permitAll()
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        // Rotte di creazione/modifica riservate agli ADMIN
                        .requestMatchers("/tornei/torneo/nuovo", "/tornei/torneo/*/modifica", "/torneo", "/tornei/torneoSalvaModificato", "/torneo/*/squadra").hasRole("ADMIN")
                        .requestMatchers("/squadra/nuova", "/squadra/*/modificaSquadra", "/squadra", "/squadra/salvaSquadraModificata").hasRole("ADMIN")
                        .requestMatchers("/giocatore/nuovo", "/giocatore").hasRole("ADMIN")
                        .requestMatchers("/partita/nuova", "/partita").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/arbitri").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/arbitri/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/arbitri/**").hasRole("ADMIN")
                        // Tutte le altre rotte (consultazione e commenti) richiedono l'autenticazione (USER o ADMIN)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailService customUserDetailService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration=new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}