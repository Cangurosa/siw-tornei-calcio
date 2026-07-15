package it.uniroma3.siw.torneo.controller;

import it.uniroma3.siw.torneo.model.Torneo;
import it.uniroma3.siw.torneo.repository.TorneoRepository;
import it.uniroma3.siw.torneo.service.PartitaService;
import it.uniroma3.siw.torneo.service.TorneoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TorneoController {
    private TorneoRepository torneoRepository;
    private TorneoService torneoService;
    private PartitaService partitaService;

    public TorneoController(TorneoService torneoService, PartitaService partitaService, TorneoRepository torneoRepository){
        this.torneoService = torneoService;
        this.partitaService = partitaService;
        this.torneoRepository = torneoRepository;
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
        model.addAttribute("torneo", torneoService.getTorneoById(id));
        return "tornei/show";
    }

    /**
     * Calendario partite del torneo
     */
    @GetMapping("/torneo/{id}/partite")
    public String getPartite(@PathVariable Long id, Model model){
        model.addAttribute("partite", partitaService.getPartiteByTorneo(id));
        return "partite/list";
    }

    /**
     * Classifica del torneo
     */
    @GetMapping("/torneo/{id}/calendario")
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

}
