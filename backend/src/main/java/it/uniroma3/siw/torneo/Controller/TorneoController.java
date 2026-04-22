package it.uniroma3.siw.torneo.Controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.torneo.Model.Torneo;
import it.uniroma3.siw.torneo.Service.TorneoService;

public class TorneoController {
    private TorneoService torneoService;

    public TorneoController(TorneoService torneoService){
        this.torneoService = torneoService;
    }

    //lista tornei
    @GetMapping("/tornei")
    public String list(Model model){
        List<Torneo> tornei = this.torneoService.getAllTornei();
        model.addAttribute("tornei", tornei);
        return "tornei.html";
    }

    // dettaglio torneo
    @GetMapping("/torneo/{id}")
    public String getTorneo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("torneo", torneoService.getTorneoById(id));
        return "torneo.html";
    }
}
