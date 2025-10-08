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
import projt4.praja.entity.dto.response.usuario.UsuarioLoginDTOResponse;

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
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public UsuarioLoginDTOResponse login(UsuarioLoginDTORequest usuarioDTOLoginRequest){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(usuarioDTOLoginRequest.getTelefone(), usuarioDTOLoginRequest.getSenha());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
		    UsuarioLoginDTOResponse usuarioDTOLoginResponse = new UsuarioLoginDTOResponse();
        usuarioDTOLoginResponse.setId(userDetails.getIdUsuario());
        usuarioDTOLoginResponse.setNome(userDetails.getNomeUsuario());
        usuarioDTOLoginResponse.setToken(jwtTokenService.generateToken(userDetails));
        return usuarioDTOLoginResponse;
    }
    public UsuarioDTOResponse criar(UsuarioDTORequest usuarioDTORequest){
        List<Role> roles = new ArrayList<Role>();
        for (int i=0; i<usuarioDTORequest.getRoleList().size(); i++){
            Role role = new Role();
            role.setName(RoleName.valueOf(usuarioDTORequest.getRoleList().get(i)));
            roles.add(role);
        }
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTORequest.getNome());
        usuario.setTelefone(usuarioDTORequest.getTelefone());
        usuario.setSenha(securityConfiguration.passwordEncoder().encode(usuarioDTORequest.getSenha()));
        usuario.setStatus(1);
        usuario.setRoles(roles);
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