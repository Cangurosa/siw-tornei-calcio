package it.uniroma3.siw.torneo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.torneo.Model.Giocatore;
import it.uniroma3.siw.torneo.Model.Squadra;
import it.uniroma3.siw.torneo.Repository.GiocatoreRepository;
import it.uniroma3.siw.torneo.Repository.SquadraRepository;

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
