package projt4.praja.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projt4.praja.entity.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u " +
            "WHERE u.status>=0 AND u.telefone=:telefone")
    Optional<Usuario> findByTelefone(@Param("telefone") String telefone);


  }