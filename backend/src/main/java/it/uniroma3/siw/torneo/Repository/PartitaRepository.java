package it.uniroma3.siw.torneo.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.torneo.Model.Partita;

@Repository
public interface PartitaRepository extends CrudRepository<Partita, Long>{

}
