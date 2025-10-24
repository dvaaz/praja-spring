package projt4.praja.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import projt4.praja.security.config.SecurityConfiguration;
import projt4.praja.entity.Usuario;
import projt4.praja.entity.dto.request.usuario.UsuarioDTORequest;
import projt4.praja.entity.dto.response.usuario.UsuarioDTOResponse;
import projt4.praja.repository.UsuarioRepository;
import projt4.praja.security.authentication.JwtTokenService;

@Service
public class UsuarioService {
    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private SecurityConfiguration securityConfiguration;
    private final UsuarioRepository usuarioRepository;
		private RoleService roleService;

		@Autowired
		public UsuarioService(AuthenticationManager authenticationManager,
		                      JwtTokenService jwtTokenService,SecurityConfiguration securityConfiguration,
		                      UsuarioRepository usuarioRepository, RoleService roleService) {
				this.authenticationManager = authenticationManager;
				this.jwtTokenService = jwtTokenService;
				this.securityConfiguration = securityConfiguration;
				this.usuarioRepository = usuarioRepository;
				this.roleService = roleService;
		}

		public UsuarioDTOResponse criar(UsuarioDTORequest dtoRequest){
				// Sem Exception para roles inexistentes
        Usuario usuario = new Usuario();
        usuario.setNome(dtoRequest.getNome());
        usuario.setTelefone(dtoRequest.getTelefone());
        usuario.setSenha(securityConfiguration.passwordEncoder().encode(dtoRequest.getSenha()));
        usuario.setStatus(1);
        usuario.setRoles((roleService.assertRole(dtoRequest.getRoleList())));
        Usuario usuariosave = usuarioRepository.save(usuario);

		    UsuarioDTOResponse usuarioDTO = new UsuarioDTOResponse();
        usuarioDTO.setId(usuariosave.getId());
        usuarioDTO.setNome(usuariosave.getNome());
        usuarioDTO.setId(usuariosave.getId());
        usuarioDTO.setTelefone(usuariosave.getTelefone());
		    usuarioDTO.getRoleList(usuariosave.getRoles()); // verificar o uso de stream
        return  usuarioDTO;
    }

}