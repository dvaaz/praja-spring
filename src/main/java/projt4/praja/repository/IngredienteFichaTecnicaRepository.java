package projt4.praja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projt4.praja.entity.FichaTecnica;
import projt4.praja.entity.IngredienteFichaTecnica;

import java.util.List;
import java.util.Optional;

public interface IngredienteFichaTecnicaRepository extends JpaRepository<IngredienteFichaTecnica,Integer> {
		@Modifying
		@Query("UPDATE IngredienteFichaTecnica f SET f.status = :status " +
				"WHERE f.id = :id")
		void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

		@Query("SELECT f FROM IngredienteFichaTecnica f WHERE f.status>=0")
		List<IngredienteFichaTecnica> listarAtivos();

		@Query("SELECT f FROM IngredienteFichaTecnica f WHERE f.fichaTecnica=:ficha AND f.status>=0")
		List<IngredienteFichaTecnica> listarIngredienteEmFichas(@Param("fichaTecnica") Integer fichaTecnica);

    @Query("SELECT f FROM IngredienteFichaTecnica f WHERE f.id =:id AND f.status>=0")
    Optional<IngredienteFichaTecnica> buscarPorId(@Param("id") Integer id);

		@Query("SELECT f FROM IngredienteFichaTecnica f " +
				"WHERE f.ingrediente.id = :ingredienteId AND f.fichaTecnica.id = :fichaTecnicaId AND f.status>=0")
    Optional<IngredienteFichaTecnica> buscarIngredienteEmFichaTecnica (
				@Param("ingredienteId") Integer ingredienteId,
				@Param("fichaTecnicaId") Integer fichaTecnicaId);

}
