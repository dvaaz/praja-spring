package projt4.praja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projt4.praja.entity.FichaTecnica;
import projt4.praja.entity.IngredienteFichaTecnica;

import java.util.List;

public interface IngredienteFichaTecnicaRepository extends JpaRepository<IngredienteFichaTecnica,Integer> {
		@Modifying
		@Query("UPDATE IngredienteFichaTecnica f SET f.status = :status " +
				"WHERE g.id = :id")
		void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

		@Query("SELECT f FROM IngredienteFichaTecnica f WHERE f.status>=0")
		List<IngredienteFichaTecnica> listarAtivos();

		@Query("SELECT f FROM IngredienteFichaTecnica f WHERE f.fichaTecnica=:ficha AND f.status>=0")
		List<IngredienteFichaTecnica> listarIngredienteDeFichas(@Param("fichaTecnica") FichaTecnica fichaTecnica);

		@Query("SELECT f FROM IngredienteFichaTecnica f " +
				"WHERE f.ingrediente.id = :ingredienteId AND f.fichaTecnica.id = :fichaTecnicaId AND f.status>=0")
		IngredienteFichaTecnica buscarIngredienteEmFichaTecnica (
				@Param("ingredienteId") Integer ingredienteId,
				@Param("fichaTecnicaId") Integer fichaTecnicaId);


}
