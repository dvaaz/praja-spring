package projt4.praja.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projt4.praja.Enum.RoleName;
import projt4.praja.entity.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleName, Integer> {
	@Query("SELECT r FROM Role r")
		public List<Role> listar();
}
