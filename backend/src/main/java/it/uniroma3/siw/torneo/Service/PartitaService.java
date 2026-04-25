package it.uniroma3.siw.torneo.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.torneo.Model.Classifica;
import it.uniroma3.siw.torneo.Model.Partita;
import it.uniroma3.siw.torneo.Model.Squadra;
import it.uniroma3.siw.torneo.Model.Torneo;
import it.uniroma3.siw.torneo.Repository.PartitaRepository;
import it.uniroma3.siw.torneo.Repository.TorneoRepository;

@Service
public class PartitaService {
    private PartitaRepository partitaRepository;
    private TorneoRepository torneoRepository;

    public PartitaService(PartitaRepository partitaRepository, TorneoRepository torneoRepository){
        this.partitaRepository = partitaRepository;
        this.torneoRepository = torneoRepository;
    }

    public List<Partita> getPartiteByTorneo(Long torneoId){
        Torneo torneo = torneoRepository.findById(torneoId).get();
        return torneo.getPartite();
    }


}
