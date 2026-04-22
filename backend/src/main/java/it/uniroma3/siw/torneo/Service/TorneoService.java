package it.uniroma3.siw.torneo.Service;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.siw.torneo.Model.Torneo;
import it.uniroma3.siw.torneo.Repository.TorneoRepository;

public class TorneoService {
    private TorneoRepository torneoRepository;

    public TorneoService(TorneoRepository torneoRepository){
        this.torneoRepository = torneoRepository;
    }

    /*Metodo per trovare tutti i tornei */
    public List<Torneo> getAllTornei(){
        List<Torneo> tornei = new ArrayList<>();
        for(Torneo torneo : this.torneoRepository.findAll()){
            tornei.add(torneo);
        }
        return tornei;
    }

    /*Metodo per vedere i dettagli di un torneo dal suo id */
    public Torneo getTorneoById(Long id){
        return this.torneoRepository.findById(id).orElse(null);
    }
}
