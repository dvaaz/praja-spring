package projt4.praja.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import projt4.praja.entity.dto.request.usuario.UsuarioLoginDTORequest;
import projt4.praja.security.TokenDTOResponse;
import projt4.praja.security.UsuarioDetailsImpl;

@Service
public class LoginService {
		private final JwtTokenService jwtTokenService;
		private final AuthenticationManager authenticationManager;

		@Autowired
		public LoginService(JwtTokenService jwtTokenService, AuthenticationManager authenticationManager) {
				this.jwtTokenService = jwtTokenService;
				this.authenticationManager = authenticationManager;
		}

		public TokenDTOResponse login(UsuarioLoginDTORequest dtoRequest){
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
						new UsernamePasswordAuthenticationToken(dtoRequest.getTelefone(), dtoRequest.getSenha());

				// Autentica o usuário com as credenciais fornecidas
				Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

				// Obtém o objeto UserDetails do usuário autenticado
				UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

				// Gera um token JWT para o usuário autenticado
				TokenDTOResponse usuarioDTOLoginResponse = new TokenDTOResponse();
				usuarioDTOLoginResponse.setToken(jwtTokenService.generateToken(userDetails));
				return usuarioDTOLoginResponse;
		}
}
