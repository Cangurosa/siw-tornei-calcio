package it.uniroma3.siw.torneo.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.torneo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente,Long> {

    Optional<Utente> findByUsername(String username);
    boolean existsByUsername(String username);
}