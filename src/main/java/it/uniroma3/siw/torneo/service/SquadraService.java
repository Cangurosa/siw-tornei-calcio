package it.uniroma3.siw.torneo.service;

import it.uniroma3.siw.torneo.model.Giocatore;
import it.uniroma3.siw.torneo.model.Squadra;
import it.uniroma3.siw.torneo.repository.GiocatoreRepository;
import it.uniroma3.siw.torneo.repository.SquadraRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SquadraService {
    private SquadraRepository squadraRepository;
    private GiocatoreRepository giocatoreRepository;

    public SquadraService(SquadraRepository squadraRepository, GiocatoreRepository giocatoreRepository){
        this.squadraRepository = squadraRepository;
        this.giocatoreRepository = giocatoreRepository;
    }

    /*Metodo per visualizzare tutte le squadre*/
    public List<Squadra> getAllSquadre(){
        List<Squadra> squadre = new ArrayList<>();
        for(Squadra squadra : this.squadraRepository.findAll()){
            squadre.add(squadra);
        }
        return squadre;
    }

    /*Metodo per visualizzare i dettagli di una squadra*/
    public Squadra getSquadraById(Long id) {
        return squadraRepository.findById(id).orElse(null);
    }

    public void saveSquadra(Squadra squadra){
        this.squadraRepository.save(squadra);
    }

    public void saveGiocatore(Giocatore giocatore) {
        this.giocatoreRepository.save(giocatore);
    }
}
