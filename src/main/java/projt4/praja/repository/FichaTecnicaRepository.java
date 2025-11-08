package projt4.praja.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import projt4.praja.entity.FichaTecnica;

import java.util.List;
import java.util.Optional;

public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Integer> {
		@Modifying
		@Transactional
		@Query("UPDATE FichaTecnica f SET f.status = :status " +
			"WHERE f.id = :id")
		void updateStatus(@Param("id") int id, @Param("status") int status) ;

		@Query("SELECT f FROM FichaTecnica f WHERE f.status >= 0")
		List<FichaTecnica> listar();

		@Query("SELECT f FROM FichaTecnica f WHERE f.status = 1")
		List<FichaTecnica> listarAtivas();

		@Query("SELECT f FROM FichaTecnica f WHERE f.id = :id AND f.status >= 0")
		Optional<FichaTecnica> buscarPorId(@Param("id") int fichaTecnicaId);

		@Query("SELECT f FROM FichaTecnica f WHERE f.grupo.id = :grupoId")
		List<FichaTecnica> listarPorGrupo(@Param("grupoId") int grupo);
		}
