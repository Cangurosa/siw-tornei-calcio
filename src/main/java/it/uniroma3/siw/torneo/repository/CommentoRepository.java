package it.uniroma3.siw.torneo.repository;

import it.uniroma3.siw.torneo.model.Commento;
import it.uniroma3.siw.torneo.model.Partita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentoRepository extends CrudRepository<Commento, Long> {
    @Query("SELECT c FROM Commento c JOIN FETCH c.autore WHERE c.partita= :partita ORDER BY c.dataCreazione ASC")
    List<Commento> findByPartitaOttimizzata(@Param("partita") Partita partita);
    /*JOIN FETCH = unica query con join tra commenti e utenti*/


    List<Commento> findByPartita(Partita partita);
    /*LAZY = query per tutti i commenti + query per prendere l'utente di ogni commento*/

}
