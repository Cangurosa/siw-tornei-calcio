package it.uniroma3.siw.torneo.service;

import it.uniroma3.siw.torneo.model.Partita;
import it.uniroma3.siw.torneo.model.Torneo;
import it.uniroma3.siw.torneo.repository.PartitaRepository;
import it.uniroma3.siw.torneo.repository.TorneoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
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

    public List<Partita> getAllPartite(){
        List<Partita> partite = new ArrayList<>();
        for(Partita partita : this.partitaRepository.findAll()){
            partite.add(partita);
        }
        return partite;
    }

    public Partita findPartitaById(Long id) throws EntityNotFoundException {
        Optional<Partita> optionalPartita = partitaRepository.findById(id);
        if(optionalPartita.isPresent()){
            Partita partita = optionalPartita.get();

            return partita;
        }
        else{
            throw new EntityNotFoundException("La partita non è stata trovata");
        }
    }

    @Transactional
    public Partita savePartita(Partita partita){
        return partitaRepository.save(partita);
    }

}
