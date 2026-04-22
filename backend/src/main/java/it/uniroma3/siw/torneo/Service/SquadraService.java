package it.uniroma3.siw.torneo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.torneo.Model.Squadra;
import it.uniroma3.siw.torneo.Repository.SquadraRepository;

@Service
public class SquadraService {
    private SquadraRepository squadraRepository;

    public SquadraService(SquadraRepository squadraRepository){
        this.squadraRepository = squadraRepository;
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


}
