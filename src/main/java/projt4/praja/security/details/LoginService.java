package projt4.praja.security.details;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import projt4.praja.entity.dto.request.security.UsuarioLoginDTORequest;
import projt4.praja.entity.dto.response.security.TokenDTOResponse;
import projt4.praja.repository.UsuarioRepository;
import projt4.praja.security.authentication.JwtTokenService;
import projt4.praja.security.config.SecurityConfiguration;

@Service
public class LoginService {

		@Autowired
		private AuthenticationManager authenticationManager;

		@Autowired
		private JwtTokenService jwtTokenService;

		@Autowired
		private UsuarioRepository repository;

		@Autowired
		private SecurityConfiguration securityConfiguration;

		// Método responsável por autenticar um usuário e retornar um token JWT
		public TokenDTOResponse authenticateUser(UsuarioLoginDTORequest loginUserDto) {
				// Cria um objeto de autenticação com o telefone e a senha do usuário
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(loginUserDto.getTelefone(), loginUserDto.getSenha());

				// Autentica o usuário com as credenciais fornecidas
				Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

				// Obtém o objeto UserDetails do usuário autenticado
				UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getAuthorities();

				// Gera um token JWT para o usuário autenticado
				return new TokenDTOResponse(jwtTokenService.generateToken(userDetails));
		}

		// Método responsável por criar um usuário

}
