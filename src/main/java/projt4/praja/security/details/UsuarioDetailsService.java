package projt4.praja.security.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projt4.praja.entity.Usuario;
import projt4.praja.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {
		UsuarioRepository repository;

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Usuario usuario = repository.findByTelefone(username)
						.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
				return new UsuarioDetailsImpl(usuario);
		}
}
