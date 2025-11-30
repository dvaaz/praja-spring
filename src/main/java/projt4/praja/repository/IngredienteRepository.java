package projt4.praja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import projt4.praja.entity.Ingrediente;

import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {

		/**
		 * Update de status direto no repository
		 * @param ingredienteId
		 * @param status
		 */
		@Modifying
	@Transactional
	@Query("UPDATE Ingrediente i SET i.status = :status " +
			"WHERE i.id = :ingredienteId")
	void updateStatus(@Param("ingredienteId") Integer ingredienteId, @Param("status") int status);

		/**
		 * Lista ingredientes com status >= 0
		 * @return
		 */
	@Query("SELECT i FROM Ingrediente i WHERE i.status>=0")
	List<Ingrediente> listar();

		/**
		 * Retorna ingrediente caso status >= 0
		 * @param ingredienteId
		 * @return
		 */
	@Query("SELECT i FROM Ingrediente i WHERE i.id = :id AND i.status>=0")
	Optional<Ingrediente> buscarPorId(@Param("ingredienteId") Integer ingredienteId);

		/**
		 * Retorna lista de ingrediente de um grupo
		 * @param grupo
		 * @return
		 */
	@Query("SELECT i FROM Ingrediente i WHERE i.grupo.id = :grupoId")
	List<Ingrediente> listarPorGrupo(@Param("grupoId") Integer grupo);
}