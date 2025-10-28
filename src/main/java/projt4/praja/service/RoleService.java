package projt4.praja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projt4.praja.Enum.RoleName;
import projt4.praja.entity.Role;
import projt4.praja.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
		private final RoleRepository repository;

		@Autowired
		public RoleService(RoleRepository repository) {
				this.repository = repository;
		}

		public List<Role> assertRole(List<RoleName> roleNames) {
				List<Role> todosRoles = repository.listar(); // storage as roles existentes > talvez um lambda tire o storage
				List<Role> roles = new ArrayList<>(); // storage as roles aprovadas

				for (RoleName roleName : roleNames) {
						for (Role role : todosRoles) {
								if (roleName.equals(role.getName())) { // Compare role names using equals
										roles.add(role);
								}
						}
				}
				return roles;
		}

		public boolean delete(Integer id){
				Optional<Role> roleOpt = repository.findById(id);
				roleOpt.ifPresent(role -> repository.delete(role));

				return roleOpt.isPresent();
		}
}
