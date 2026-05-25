package it.uniroma3.siw.torneo.repository;

import it.uniroma3.siw.torneo.model.Torneo;
import org.springframework.data.repository.CrudRepository;

public interface TorneoRepository extends CrudRepository<Torneo, Long> {
}
