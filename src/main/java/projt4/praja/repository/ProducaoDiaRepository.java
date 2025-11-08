package projt4.praja.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projt4.praja.entity.ProducaoDia;

@Repository
public interface ProducaoDiaRepository extends JpaRepository<ProducaoDia, Integer> {
		@Modifying
		@Transactional
		@Query("UPDATE p FROM ProducaoDia p " +
				"WHERE p.status >= 0 AND p.id = :id")
		void
}
