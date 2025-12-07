package projt4.praja.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projt4.praja.entity.Estoque;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
		/**
		 * Alterar Quantidade disponivel em estoque por id
		 * @param id
		 * @param qtd
		 */
		@Modifying
		@Transactional
		@Query("Update Estoque e SET e.quantidade = :qtd " +
				"WHERE e.id = :estoqueId")
		void updateQtd(@Param("estoqueId") int id, @Param("qtd") int qtd);

		/**
		 * Alteração de status de estoque 0 para inativo 1 para ativo
		 * @param estoqueId @param status
		 */
		@Modifying
		@Transactional
		@Query("UPDATE Estoque e SET e.status = :status " +
				"WHERE e.id= :estoqueId")
		void updateStatus(@Param("estoqueId") int estoqueId, @Param("status") int status);


		/**
		 * Busca de Estoques por Id do Ingrediente, use 0 para todos os Estoques e 1 apenas para Estoques ativos
		 * @param ingredienteId
		 * @return
		 */
		@Query("SELECT e FROM Estoque e " +
				"WHERE e.status >= :status AND e.ingrediente.id = :ingredienteId")
		List<Estoque> listByIngredienteId(@Param("ingredienteId") int ingredienteId, @Param("status") int status);

		/**
		 * Busca por estoque só retorna se tiver com status >= 0
		 *
		 * @param id
		 * @return
		 */
		@Query("SELECT e FROM Estoque e " +
				"WHERE e.id =:id AND e.status >= 0")
		Optional<Estoque> buscarPorId(@Param("id") int id);
}
