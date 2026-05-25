package it.uniroma3.siw.torneo.controller.controller;

import it.uniroma3.siw.torneo.model.Giocatore;
import it.uniroma3.siw.torneo.model.Squadra;
import it.uniroma3.siw.torneo.repository.SquadraRepository;
import it.uniroma3.siw.torneo.service.SquadraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SquadraController {
    private SquadraRepository squadraRepository;
    private SquadraService squadraService;

    public SquadraController(SquadraService squadraService, SquadraRepository squadraRepository){
        this.squadraService = squadraService;
        this.squadraRepository = squadraRepository;
    }

    /**
     * Lista squadre
     */
    @GetMapping("/squadre")
    public String list(Model model) {
        List<Squadra> squadre = this.squadraService.getAllSquadre();
        model.addAttribute("squadre", squadre);
        return "squadre/list";
    }

    /**
     * Trova una squadra dal suo id
     */
    @GetMapping("/squadra/{id:\\d+}")
    public String getSquadra(@PathVariable("id") Long id, Model model) {
        model.addAttribute("squadra", squadraService.getSquadraById(id));
        return "squadre/show";
    }

    /**
     * Creazione di una nuova Squadra
     */
    @GetMapping("/squadra/nuova")
    public String nuovaSquadra (Model model){
        model.addAttribute("squadra", new Squadra());
        return "squadre/form";
    }

    @PostMapping("/squadra")
    public String salvaSquadra(@ModelAttribute Squadra squadra) {
        squadraService.saveSquadra(squadra);
        return "redirect:/squadre";
    }

    /**
     * Inserimento di un nuovo giocatore in uno squadra
     */
    @GetMapping("/giocatore/nuovo")
    public String nuovoGiocatore(Model model) {
        model.addAttribute("giocatore", new Giocatore());
        model.addAttribute("squadre", this.squadraService.getAllSquadre());
        return "giocatori/form";
    }

    @PostMapping("/giocatore")
    public String salvaGiocatore(@ModelAttribute Giocatore giocatore, @RequestParam("squadraId") Long squadraId) {
        Squadra squadra = squadraService.getSquadraById(squadraId);
        giocatore.setSquadra(squadra);
        squadraService.saveGiocatore(giocatore);
        return "redirect:/squadra/" + squadraId;
    }

    /**
     * Modifica di una squadra
     */
    @GetMapping("/squadra/{id}/modificaSquadra")
    public String modificaSquadra(@PathVariable Long id, Model model){
        model.addAttribute("squadra", squadraService.getSquadraById(id));
        return "squadre/edit.html";
    }

    @PostMapping("/squadra/salvaSquadraModificata")
    public String salvaSquadraModificata(@ModelAttribute Squadra squadra){
        this.squadraRepository.save(squadra);
        return "redirect:/squadra/" + squadra.getId();
    }


}
