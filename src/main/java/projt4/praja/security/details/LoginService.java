package projt4.praja.security.details;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import projt4.praja.entity.dto.request.security.UsuarioLoginDTORequest;
import projt4.praja.entity.dto.response.security.TokenDTOResponse;
import projt4.praja.repository.UsuarioRepository;
import projt4.praja.security.authentication.JwtTokenService;
import projt4.praja.security.config.SecurityConfiguration;

@Service
public class LoginService {
		private AuthenticationManager authenticationManager;
		private JwtTokenService jwtTokenService;
		private UsuarioRepository repository;
		private SecurityConfiguration securityConfiguration;

		@Autowired
		public LoginService(AuthenticationManager authenticationManager
				, JwtTokenService jwtTokenService, UsuarioRepository repository
				, SecurityConfiguration securityConfiguration) {
				this.authenticationManager = authenticationManager;
				this.jwtTokenService = jwtTokenService;
				this.repository = repository;
				this.securityConfiguration = securityConfiguration;
		}

		/**
		 * Método responsável por autenticar um usuário e retornar um token JWT
 		 */

		public TokenDTOResponse authenticateUser(UsuarioLoginDTORequest loginUserDto) {
				// Cria um objeto de autenticação com o telefone e a senha do utilizador
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(loginUserDto.telefone(), loginUserDto.senha());

				// Autentica o usuário com as credenciais fornecidas
				Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

				// Obtém o objeto UserDetails do usuário autenticado
				UsuarioDetailsImpl usuarioDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

				// Gera um token JWT para o usuário autenticado
				return new TokenDTOResponse(jwtTokenService.generateToken(usuarioDetails));
		}


}
