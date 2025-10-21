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

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
        "/api/usuario/login", // Url que usaremos para fazer login
        "/api/usuario/criar", // Url que usaremos para criar um usuário
        // 🔓 Swagger/OpenAPI UI
        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/api/fichatecnica/listar/dia"
    };
    // Endpoints que requerem autenticação para serem acessados
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
        "/api/fichatecnica/listar/dia",
        "/api/fichatecnica/listar",
        "/api/fichatecnica/buscar"
    };
    // Endpoints que só podem ser acessador por usuários com permissão de CLIENTE
    public static final String [] ENDPOINTS_CLIENTE = {
		    "/api/fichatecnica/listar/dia",
		    "/api/fichatecnica/listar",
		    "/api/fichatecnica/buscar"
    };
		// Endpoints que só podem ser acessador por usuários com permissão de VENDEDORES
		public static final String [] ENDPOINTS_VENDAS = {
				"/api/usuario/listar",
				"/api/fichatecnica/listar/dia",
				"/api/fichatecnica/listar",
				"/api/fichatecnica/buscar"
		};
		// Endpoints que só podem ser acessador por usuários com permissão de COZINHEIRO
		public static final String [] ENDPOINTS_COZINHA = {
				// ficha tecnica
				"/api/fichatecnica/criar",
				"/api/fichatecnica/listar",
				"/api/fichatecnica/buscar",
				"/api/fichatecnica/listar/dia",
				"/api/fichatecnica/buscar",
				//ingrediente
				"api/ingrediente/criar",
				"api/ingrediente/listar",
				"api/ingrediente/buscar",
				"api/ingrediente/atualizar",
				//grupo
				"api/grupo/listar"
		};
		// Endpoints que só podem ser acessador por usuários com permissão de cliente
		public static final String [] ENDPOINTS_ESTOQUE = {
				"/api/ingrediente/listar"

		};
    // Endpoints que só podem ser acessador por usuários com permissão de ADMINISTRADOR
    public static final String [] ENDPOINTS_ADMINNISTRADOR = {
				"/api"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() //adicionado para funcionamento do swagger
                        .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
		                    .anyRequest().denyAll()
                )
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}