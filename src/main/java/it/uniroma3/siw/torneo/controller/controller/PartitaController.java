package it.uniroma3.siw.torneo.controller.controller;

import it.uniroma3.siw.torneo.model.Partita;
import it.uniroma3.siw.torneo.service.PartitaService;
import it.uniroma3.siw.torneo.service.SquadraService;
import it.uniroma3.siw.torneo.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PartitaController {
    private final SquadraService squadraService;
    private final PartitaService partitaService;
    private final TorneoService torneoService;

    public PartitaController(PartitaService partitaService, SquadraService squadraService, TorneoService torneoService){
        this.partitaService = partitaService;
        this.squadraService = squadraService;
        this.torneoService = torneoService;
    }

    /**
     * Creazione di una nuova partita
     */
    @GetMapping("/partita/nuova")
    public String nuovaPartita(Model model){
        model.addAttribute("partita", new Partita()); //oggetto vuoto
        model.addAttribute("squadre", squadraService.getAllSquadre());
        model.addAttribute("tornei", torneoService.getAllTornei());
        return "partite/form";
    }

    /**
     * Mostra la lista di tutte le partite
     */
    @GetMapping("/partite")
    public String listPartite(Model model) {
        List<Partita> partite = this.partitaService.getAllPartite();
        model.addAttribute("partite", partite);
        return "partite/list";
    }

    //riceve e salva i dati dal form
    @PostMapping("/partita")
    public String salvaPartita(@ModelAttribute Partita partita,
                               @RequestParam("squadraHomeId") Long squadraHomeId,
                               @RequestParam("squadraAwayId") Long squadraAwayId,
                               @RequestParam("torneoId") Long torneoId) {
        partita.setSquadraHome(squadraService.getSquadraById(squadraHomeId));
        partita.setSquadraAway(squadraService.getSquadraById(squadraAwayId));
        partita.setTorneo(torneoService.getTorneoById(torneoId));
        partitaService.savePartita(partita);
        return "redirect:/partite";
    }
}
