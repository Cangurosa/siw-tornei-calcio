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
import it.uniroma3.siw.torneo.Repository.TorneoRepository;

@Service
public class TorneoService {
    private TorneoRepository torneoRepository;

    public TorneoService(TorneoRepository torneoRepository) {
        this.torneoRepository = torneoRepository;
    }

    /* Metodo per trovare tutti i tornei */
    public List<Torneo> getAllTornei() {
        List<Torneo> tornei = new ArrayList<>();
        for (Torneo torneo : this.torneoRepository.findAll()) {
            tornei.add(torneo);
        }
        return tornei;
    }

    /* Metodo per vedere i dettagli di un torneo dal suo id */
    public Torneo getTorneoById(Long id) {
        return this.torneoRepository.findById(id).orElse(null);
    }

    // metodo per creare la classifica delle squadre al torneo
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
            if (!"FINITA".equals(p.getStato()))
                continue;

            Squadra home = p.getSquadraHome();
            Squadra away = p.getSquadraAway();

            if (!punti.containsKey(home) || !punti.containsKey(away))
                continue;

            if (p.getGoalsHome() > p.getGoalsAway()) {
                punti.put(home, punti.get(home) + 3);
            } else if (p.getGoalsHome() < p.getGoalsAway()) {
                punti.put(away, punti.get(away) + 3);
            } else {
                punti.put(home, punti.get(home) + 1);
                punti.put(away, punti.get(away) + 1);
            }
        }

        // trasformo in lista
        List<Classifica> risultato = new ArrayList<>();
        for (Squadra s : punti.keySet()) {
            risultato.add(new Classifica(s, punti.get(s)));
        }

        // ordino
        risultato.sort((a, b) -> b.getPunti() - a.getPunti());
        return risultato;
    }
}
