package projt4.praja.security.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projt4.praja.repository.UsuarioRepository;
import projt4.praja.security.config.SecurityConfiguration;
import projt4.praja.security.details.UsuarioDetailsImpl;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException {

        // Remove contexto base (ex: /praja)
        String requestURI = request.getRequestURI().replaceFirst(request.getContextPath(), "");

        // 🔓 Se o endpoint for público, apenas segue o fluxo
        if (isPublicEndpoint(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 🔐 Tenta recuperar e validar o token
        String token = recoveryToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String subject = jwtTokenService.getSubjectFromToken(token);
            if (subject != null) {
                usuarioRepository.findByTelefone(subject).ifPresent(usuario -> {
                    UsuarioDetailsImpl userDetails = new UsuarioDetailsImpl(usuario);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
            }
        } catch (Exception e) {
            // Token inválido → ignora e segue sem travar a requisição
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private boolean isPublicEndpoint(String uri) {
        return Arrays.stream(SecurityConfiguration.PUBLIC_ENDPOINTS)
            .anyMatch(publicEndpoint ->
                uri.startsWith(publicEndpoint.replace("/**", ""))
            );
    }
}
