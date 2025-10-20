package projt4.praja.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import projt4.praja.Enum.RoleName;
import projt4.praja.config.SecurityConfiguration;
import projt4.praja.entity.Role;
import projt4.praja.entity.Usuario;
import projt4.praja.entity.dto.request.usuario.UsuarioDTORequest;
import projt4.praja.entity.dto.request.usuario.UsuarioLoginDTORequest;
import projt4.praja.entity.dto.response.usuario.UsuarioDTOResponse;
import projt4.praja.repository.UsuarioRepository;
import projt4.praja.security.TokenDTOResponse;
import projt4.praja.security.service.JwtTokenService;
import projt4.praja.security.UsuarioDetailsImpl;
import projt4.praja.security.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    private final UsuarioRepository usuarioRepository;
		@Autowired
		private RoleService roleService;

		public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
//		    usuarioDTO.getRoleList(usuariosave.getRoles().stream()); // verificar o uso de stream
        return  usuarioDTO;
    }

}