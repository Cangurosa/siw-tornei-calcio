package it.uniroma3.siw.torneo.service;

import it.uniroma3.siw.torneo.model.Classifica;
import it.uniroma3.siw.torneo.model.Partita;
import it.uniroma3.siw.torneo.model.Squadra;
import it.uniroma3.siw.torneo.model.Torneo;
import it.uniroma3.siw.torneo.repository.TorneoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class TorneoService {
    private TorneoRepository torneoRepository;

    public TorneoService(TorneoRepository torneoRepository){
        this.torneoRepository = torneoRepository;
    }

    /**
     * Metodo per trovare tutti i tornei
     */
    public List<Torneo> getAllTornei(){
        List<Torneo> tornei = new ArrayList<>();
        for(Torneo torneo : this.torneoRepository.findAll()){
            tornei.add(torneo);
        }
        return tornei;
    }

    /**
     * Metodo per vedere i dettagli di un torneo
     */
    public Torneo getTorneoById(Long id){
        return this.torneoRepository.findById(id).orElse(null);
    }

    /**
     * Metodo per creare la classifica del torneo
     */
    public List<Classifica> getClassifica(Long torneoId) {
        Torneo torneo = torneoRepository.findById(torneoId).orElse(null);
        if (torneo == null)
            return new ArrayList<>();

        System.out.println("TORNEO: " + torneo);
        System.out.println("PARTITE: " + torneo.getPartite());
        System.out.println("SQUADRE: " + torneo.getSquadre());
        List<Squadra> squadre = torneo.getSquadre();
        List<Partita> partite = torneo.getPartite();

        Map<Squadra, Integer> punti = new HashMap<>();

        for (Squadra s : torneo.getSquadre()) {
            punti.put(s, 0);
        }

        for (Partita p : torneo.getPartite()) {
            if (!"CONCLUSA".equals(p.getStato()) && !"FINITA".equals(p.getStato()))
                continue;

            Squadra home = p.getSquadraHome();
            Squadra away = p.getSquadraAway();

            if (home == null || away == null)
                continue;

            // Inserisce dinamicamente la squadra in classifica se ha giocato un match nel torneo (gestione dell'incoerenza dei dati)
            if (!punti.containsKey(home)) {
                punti.put(home, 0);
            }
            if (!punti.containsKey(away)) {
                punti.put(away, 0);
            }

            if (p.getGoalsHome() > p.getGoalsAway()) {
                punti.put(home, punti.get(home) + 3);
            } else if (p.getGoalsHome() < p.getGoalsAway()) {
                punti.put(away, punti.get(away) + 3);
            } else {
                punti.put(home, punti.get(home) + 1);
                punti.put(away, punti.get(away) + 1);
            }
        }
        List<Classifica> risultato = new ArrayList<>();
        for (Squadra s : punti.keySet()) {
            risultato.add(new Classifica(s, punti.get(s)));
        }
        risultato.sort((a, b) -> b.getPunti() - a.getPunti());
        return risultato;
    }

    @Transactional
    public void save(Torneo torneo){
        this.torneoRepository.save(torneo);
    }

}
