package projt4.praja.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projt4.praja.Enum.RoleName;
import projt4.praja.entity.Role;
import projt4.praja.repository.security.RoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
		private final RoleRepository repository;

		@Autowired
		public RoleService(RoleRepository repository) {
				this.repository = repository;
		}

		public List<Role> assertRole(List<RoleName> roleNames) {
				List<Role> todosRoles = repository.listar(); // armazena as roles existentes
				List<Role> roles = new ArrayList<>(); // armazena as roles aprovadas

				for (RoleName roleName : roleNames) {
						for (Role role : todosRoles) {
								if (roleName.equals(role.getName())) { // Compare role names using equals
										roles.add(role);
								}
						}
				}
				return roles;
		}
}
