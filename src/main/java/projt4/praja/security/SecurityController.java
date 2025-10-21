package projt4.praja.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projt4.praja.entity.dto.request.security.UsuarioLoginDTORequest;
import projt4.praja.entity.dto.response.security.TokenDTOResponse;
import projt4.praja.security.details.LoginService;

@RestController
@RequestMapping("/auth")
@Tag(name="Endpoint de securança", description ="Endpoint para lidar com logins, alterações de senhas e outros aspectos de segurança")
public class SecurityController {
		private final LoginService loginService;

		@Autowired
		public SecurityController(LoginService loginService) {
				this.loginService = loginService;
		}

		@PostMapping("/login")
		public ResponseEntity<TokenDTOResponse> login(@RequestBody UsuarioLoginDTORequest dtoRequest){
				return ResponseEntity.ok(loginService.login(dtoRequest));
		}
}
