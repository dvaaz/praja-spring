package projt4.praja.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import projt4.praja.security.authentication.UserAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    // Endpoints públicos
    public static final String[] PUBLIC_ENDPOINTS = {
        "/api/auth/login",
        "/api/usuario/criar",
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html"
    };

    // Outros endpoints com restrição de roles
    public static final String[] ENDPOINTS_CLIENTE = {
        "/api/fichatecnica/listar/**"
    };

    public static final String[] ENDPOINTS_VENDAS = {
        "/api/usuario/listar",
        "/api/fichatecnica/**"
    };

    public static final String[] ENDPOINTS_COZINHA = {
        "/api/fichatecnica/**",
        "/api/ingrediente/**",
        "/api/grupo/**"
    };

    public static final String[] ENDPOINTS_ESTOQUE = {
        "/api/ingrediente/listar"
    };

    public static final String[] ENDPOINTS_ADMINISTRADOR = {
        "/api/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_ENDPOINTS)
                    .permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                .requestMatchers(ENDPOINTS_CLIENTE)
                    .hasAnyRole("CLIENTE", "VENDAS", "COZINHA", "ADMINISTRADOR", "ESTOQUE")
                .requestMatchers(ENDPOINTS_VENDAS)
                    .hasAnyRole("VENDAS", "ADMINISTRADOR", "COZINHA")
                .requestMatchers(ENDPOINTS_COZINHA)
                    .hasAnyRole("COZINHA", "ADMINISTRADOR")
                .requestMatchers(ENDPOINTS_ESTOQUE)
                    .hasAnyRole("ESTOQUE", "ADMINISTRADOR", "COZINHA")
                .requestMatchers(ENDPOINTS_ADMINISTRADOR)
                    .hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
