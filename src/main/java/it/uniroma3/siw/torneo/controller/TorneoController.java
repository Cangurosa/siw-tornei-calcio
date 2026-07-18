package it.uniroma3.siw.torneo.controller;

import it.uniroma3.siw.torneo.model.Torneo;
import it.uniroma3.siw.torneo.model.Squadra;
import it.uniroma3.siw.torneo.repository.TorneoRepository;
import it.uniroma3.siw.torneo.service.PartitaService;
import it.uniroma3.siw.torneo.service.TorneoService;
import it.uniroma3.siw.torneo.service.SquadraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@Controller
public class TorneoController {
    private TorneoRepository torneoRepository;
    private TorneoService torneoService;
    private PartitaService partitaService;
    private SquadraService squadraService;

    public TorneoController(TorneoService torneoService, PartitaService partitaService, TorneoRepository torneoRepository, SquadraService squadraService){
        this.torneoService = torneoService;
        this.partitaService = partitaService;
        this.torneoRepository = torneoRepository;
        this.squadraService = squadraService;
    }

    /**
     * Lista tornei
     */
    @GetMapping("/tornei")
    public String list(Model model){
        List<Torneo> tornei = this.torneoService.getAllTornei();
        model.addAttribute("tornei", tornei);
        return "tornei/list";
    }

    /**
     * Dettagli torneo
     */
    @GetMapping("/torneo/{id}")
    public String getTorneo(@PathVariable("id") Long id, Model model){
        Torneo torneo = torneoService.getTorneoById(id);
        model.addAttribute("torneo", torneo);
        model.addAttribute("classifica", torneoService.getClassifica(id));

        // Filtra le squadre non ancora iscritte a questo torneo
        List<Squadra> squadreMancanti = new ArrayList<>(squadraService.getAllSquadre());
        squadreMancanti.removeAll(torneo.getSquadre());
        model.addAttribute("squadreMancanti", squadreMancanti);

        return "tornei/show";
    }

    /**
     * Calendario partite del torneo
     */
    @GetMapping("/torneo/{id}/partite")
    public String getPartite(@PathVariable Long id, Model model){
        model.addAttribute("partite", partitaService.getPartiteByTorneo(id));
        model.addAttribute("classifica", torneoService.getClassifica(id));
        model.addAttribute("torneo", torneoService.getTorneoById(id));
        return "partite/list";
    }

    /**
     * Classifica del torneo
     */
    @GetMapping("/torneo/{id}/classifica")
    public String getClassifica(@PathVariable Long id, Model model){
        model.addAttribute("classifica", torneoService.getClassifica(id));
        return "tornei/classifica";
    }

    /**
     * Creazione nuovo Torneo
     */
    @GetMapping("/tornei/torneo/nuovo")
    public String formNuovoTorneo(Model model) {
        model.addAttribute("torneo", new Torneo());
        return "tornei/form";
    }


    @PostMapping("/torneo")
    public String salvaTorneo(@ModelAttribute Torneo torneo) {
        System.out.println("TORNEO: " + torneo.getNome());
        System.out.println("ANNO: " + torneo.getAnno());
        torneoRepository.save(torneo);
        return "redirect:/tornei";
    }

    /**
     * Modifica di un Torneo
     */
    @GetMapping("/tornei/torneo/{id}/modifica")
    public String modificaTorneo(@PathVariable Long id, Model model){
        model.addAttribute("torneo", torneoService.getTorneoById(id));
        return "tornei/edit";
    }

    @PostMapping("/tornei/torneoSalvaModificato")
    public String salvaTorneoModificato(@ModelAttribute Torneo torneo){
        this.torneoRepository.save(torneo);
        return "redirect:/tornei";
    }

    @PostMapping("/torneo/{id}/squadra")
    public String aggiungiSquadraAlTorneo(@PathVariable("id") Long torneoId, @RequestParam("squadraId") Long squadraId) {
        Torneo torneo = torneoService.getTorneoById(torneoId);
        Squadra squadra = squadraService.getSquadraById(squadraId);
        if (torneo != null && squadra != null && !torneo.getSquadre().contains(squadra)) {
            torneo.getSquadre().add(squadra);
            torneoService.save(torneo);
        }
        return "redirect:/torneo/" + torneoId;
    }

}
